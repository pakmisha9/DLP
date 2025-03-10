package com.diploma.dlp.controllers;

import com.diploma.dlp.dto.AuthRequest;
import com.diploma.dlp.dto.RefreshTokenRequest;
import com.diploma.dlp.entities.User;
import com.diploma.dlp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/users")
    public ResponseEntity<?> createUser (@RequestBody User user) {
        return ResponseEntity.ok(authService.createUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<?> login (@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.login(authRequest));
    }

    @PostMapping("/access-token")
    public ResponseEntity<?> accessToken (@RequestBody RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.getAccessToken(refreshTokenRequest.getRefreshToken()));
    }
}
