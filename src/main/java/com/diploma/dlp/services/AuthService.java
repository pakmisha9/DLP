package com.diploma.dlp.services;
import com.diploma.dlp.dto.LoginRequestDTO;
import com.diploma.dlp.dto.AuthResponseDTO;
import com.diploma.dlp.dto.CreateUserDTO;
import com.diploma.dlp.entities.User;
import com.diploma.dlp.mappers.UserMapper;
import com.diploma.dlp.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtTokenService jwtTokenService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserMapper userMapper;
    public ResponseEntity<?> createUser(CreateUserDTO createUserDTO) {
        try {
            if(userRepository.findByUsername(createUserDTO.getUsername()).isPresent()) {
                return new ResponseEntity<>("User already exist", HttpStatus.CONFLICT);
            } else if (userRepository.findByPhoneNumber(createUserDTO.getPhoneNumber()).isPresent()) {
                return new ResponseEntity<>("Phone number already exists", HttpStatus.CONFLICT);
            }else if (userRepository.findByEmail(createUserDTO.getEmail()).isPresent()) {
                return new ResponseEntity<>("Email already exists", HttpStatus.CONFLICT);
            }
            User user = userMapper.toUser(createUserDTO);
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            Map<String, Object> response = new HashMap<>();
            response.put("message", "SUCCESS");
            response.put("data", userMapper.toUserDTO(user));
            response.put("httpStatus", HttpStatus.CREATED);
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Internal error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    public ResponseEntity<?> login(LoginRequestDTO request) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);
            User user = userRepository.findByUsername(request.getUsername()).orElseThrow();

            String accessToken = jwtTokenService.generateAccessToken(user);
            String refreshToken = jwtTokenService.generateRefreshToken(user);

            return ResponseEntity.ok(new AuthResponseDTO(accessToken, refreshToken));
        } catch (AuthenticationException e) {
            return new ResponseEntity<>("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (Exception e){
            return new ResponseEntity<>("Internal error!", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> getAccessToken(String refreshToken) {
        String username = jwtTokenService.getUsernameFromToken(refreshToken);
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No user found"));

        if (jwtTokenService.isValidRefreshToken(refreshToken, user.getUsername())) {
            String newAccessToken = jwtTokenService.generateAccessToken(user);
            return ResponseEntity.ok(new AuthResponseDTO(newAccessToken, refreshToken));
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }
}
