package com.library.service.impl;

import com.library.dto.LoginRequest;
import com.library.dto.LoginResponse;
import com.library.model.User;
import com.library.repository.UserRepository;
import com.library.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public LoginResponse login(LoginRequest request) {

        // 1. Check if user exists
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Invalid email or password"));

        // 2. Validate password
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Invalid email or password");
        }

        // 3. Return response
        return LoginResponse.builder()
                .message("Login successful")
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}