package com.diploma.dlp.dto;

import com.diploma.dlp.constants.Role;
import lombok.Data;
@Data
public class UserDTO {
    private String username;
    private Role role;
    private String phoneNumber;
    private String email;
}
