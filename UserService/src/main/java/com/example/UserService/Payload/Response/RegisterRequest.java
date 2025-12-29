package com.example.UserService.Payload.Response;

import com.example.UserService.Payload.DTOS.AddressDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private List<AddressDTO> addressList;
}
