package com.example.UserService.Service.Services;

import com.example.UserService.Entity.Address;
import com.example.UserService.Entity.Role;
import com.example.UserService.Entity.User;
import com.example.UserService.GlobalExceptionHandler.Exceptions.AlreadyExistsException;
import com.example.UserService.GlobalExceptionHandler.Exceptions.ResourceNotFoundException;
import com.example.UserService.Payload.DTOS.UserDTO;

import com.example.UserService.Payload.Response.RegisterRequest;
import com.example.UserService.Repository.RoleRepository;
import com.example.UserService.Repository.UserRepository;
import com.example.UserService.Service.Interfaces.IUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @Override
    public String createUser(RegisterRequest registerRequest) {
        User user = new User();
        Role role = roleRepository.findByRoleName("ROLE_USER").orElseThrow(()->new ResourceNotFoundException("Role not found:"));
        List<Address> addressesList = registerRequest.getAddressList().stream().map(addressDTO -> modelMapper.map(addressDTO, Address.class)).toList();
        user.setUsername(registerRequest.getUsername());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        user.setEmail(registerRequest.getEmail());
        user.setAddressList(addressesList);
        user.addRole(role);
        Boolean isEmailExist = userRepository.existsByEmail(user.getEmail());
        Boolean isUsernameExist = userRepository.existsByUsername(user.getUsername());

        if(isUsernameExist)
            throw new AlreadyExistsException("This username already exists");
        else if(isEmailExist)
            throw new AlreadyExistsException("This email already registered");

        if (user.getAddressList() != null) {
            for (Address address : user.getAddressList()) {
                address.setUser(user);
            }
        }
        userRepository.save(user);
        return "User successfully created";
    }

    @Override
    public List<UserDTO> findAllUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(user -> modelMapper.map(user, UserDTO.class)).toList();
    }

    @Override
    public List<UserDTO> findUserByKeyword(String keyword) {
        List<User> userList = userRepository.findByUsernameContainingIgnoreCase(keyword);
        return userList.stream().map(user -> modelMapper.map(user,UserDTO.class)).toList();
    }

    @Override
    public UserDTO findUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found userId:" + userId));
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public String deleteUser(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not found userId:" + userId));
        user.removeAllRoles();
        for(Address address : user.getAddressList())
            user.removeAddress(address);
        userRepository.delete(user);
        return "User successfully deleted";
    }

    @Override
    public UserDTO updateUser(Long userId, UserDTO userDTO) {
        User user = modelMapper.map(userDTO,User.class);
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found userId:"+userId));

        List<Address> incomingAddresses = getAddresses(user, existingUser);
        existingUser.getAddressList().clear();

        if (incomingAddresses != null) {
            for (Address incomingAddress : incomingAddresses) {
                existingUser.addAddress(incomingAddress);
            }
        }
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        userRepository.save(existingUser);
        return modelMapper.map(existingUser, UserDTO.class);
    }

    @Override
    public UserDTO findUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException("Username not found"+username));;
        return modelMapper.map(user,UserDTO.class);
    }

    private static List<Address> getAddresses(User user, User existingUser) {
        List<Address> existingAddresses = existingUser.getAddressList();
        List<Address> incomingAddresses = user.getAddressList();
        if (incomingAddresses != null) {
            for (int i = 0; i < incomingAddresses.size(); i++) {
                Address incoming = incomingAddresses.get(i);
                if (incoming.getAddressId() == null && i < existingAddresses.size()) {
                    incoming.setAddressId(existingAddresses.get(i).getAddressId());
                }
            }
        }
        return incomingAddresses;
    }
}