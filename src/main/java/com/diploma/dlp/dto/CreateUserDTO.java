package com.diploma.dlp.dto;

import com.diploma.dlp.constants.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
public class CreateUserDTO {
    @NotBlank(message = "Please provide a username")
    private String username;
    @NotBlank(message = "Please enter your phone number")
    @Pattern(regexp = "\\+?[0-9]+", message = "Phone number must contain only digits and may start with a '+'")
    private String phoneNumber;
    @NotBlank(message = "Please provide an email")
    @Email(message = "Email should be valid")
    private String email;
    @Size(min = 8, message = "Password must be at least 8 characters long")
    private String password;
    @Setter
    private Role role;
}
