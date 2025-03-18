package com.diploma.dlp.controllers;

import com.diploma.dlp.dto.LoginRequestDTO;
import com.diploma.dlp.dto.RefreshTokenRequestDTO;
import com.diploma.dlp.dto.CreateUserDTO;
import com.diploma.dlp.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<?> createUser (@Valid @RequestBody CreateUserDTO createUserDTO) {
        return ResponseEntity.ok(authService.createUser(createUserDTO));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody LoginRequestDTO loginRequestDTO) {
        return ResponseEntity.ok(authService.login(loginRequestDTO));
    }

    @PostMapping("/access-token")
    public ResponseEntity<?> accessToken (@RequestBody RefreshTokenRequestDTO refreshTokenRequestDTO) {
        return ResponseEntity.ok(authService.getAccessToken(refreshTokenRequestDTO.getRefreshToken()));
    }
}
