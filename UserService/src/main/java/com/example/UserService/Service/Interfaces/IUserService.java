package com.example.UserService.Service.Interfaces;

import com.example.UserService.Payload.DTOS.UserDTO;
import com.example.UserService.Payload.Response.RegisterRequest;

import java.util.List;

public interface IUserService {
    List<UserDTO> findAllUsers();
    UserDTO findUserById(Long userId);
    String createUser(RegisterRequest registerRequest);
    String deleteUser(Long userId);
    UserDTO updateUser(Long userId, UserDTO userDTO);
    List<UserDTO> findUserByKeyword(String keyword);
    UserDTO findUserByUsername(String username);
}
