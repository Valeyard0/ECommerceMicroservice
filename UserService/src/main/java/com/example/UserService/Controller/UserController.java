package com.example.UserService.Controller;

import com.example.UserService.Payload.DTOS.UserDTO;
import com.example.UserService.Payload.Response.RegisterRequest;
import com.example.UserService.Service.Interfaces.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1")
@RequiredArgsConstructor
public class UserController {
    private final IUserService userService;

    @PostMapping("/users/createUser")
    public ResponseEntity<String> createUser(@RequestBody @Valid RegisterRequest registerRequest){
        String message = userService.createUser(registerRequest);
        return new ResponseEntity<>(message,HttpStatus.CREATED);
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDTO>> findAllUsers(){
        List<UserDTO> userList = userService.findAllUsers();
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/username/{username}")
    public ResponseEntity<UserDTO> findUserByUsername(@PathVariable String username){
        UserDTO userDTO = userService.findUserByUsername(username);
        return ResponseEntity.ok(userDTO);
    }

    @GetMapping("/users/keyword/{keyword}")
    public ResponseEntity<List<UserDTO>> findUserByKeyword(@PathVariable String keyword){
        List<UserDTO> userList = userService.findUserByKeyword(keyword);
        return ResponseEntity.ok(userList);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<UserDTO> findUserById(@PathVariable Long userId){
        UserDTO user = userService.findUserById(userId);
        return ResponseEntity.ok(user);
    }

    @DeleteMapping("/users/{userId}")
    @PreAuthorize("isAuthenticated() and (#id.toString() == principal.userId.toString() or hasRole('ADMIN'))")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId){
        String message = userService.deleteUser(userId);
        return ResponseEntity.ok(message);
    }

    @PutMapping("/users/userId/{userId}")
    @PreAuthorize("isAuthenticated() and (#id.toString() == principal.userId.toString() or hasRole('ADMIN'))")
    public ResponseEntity<UserDTO> updateUser(@PathVariable("userId") Long id, @RequestBody @Valid UserDTO userDTO) {
        UserDTO updatedUser = userService.updateUser(id, userDTO);
        return ResponseEntity.ok(updatedUser);
    }
}
