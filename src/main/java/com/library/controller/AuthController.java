package com.library.controller;

import com.library.dto.LoginRequest;
import com.library.dto.LoginResponse;
import com.library.dto.SignupRequest;
import com.library.model.Role;
import com.library.model.User;
import com.library.service.AuthService;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest request) {
        try {
            User user = userService.registerUser(request);

            String message;
            if (user.getRole() == Role.ADMIN) {
                message = "Admin registered successfully with email: " + user.getEmail();
            } else {
                message = "User registered successfully with email: " + user.getEmail();
            }

            return ResponseEntity.ok(message);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }
}