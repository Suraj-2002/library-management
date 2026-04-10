package com.library.service;

import com.library.dto.SignupRequest;
import com.library.model.Role;
import com.library.model.User;
import com.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.lang.NonNull;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ removed @Autowired, added final

    @SuppressWarnings("null")
    public @NonNull User registerUser(SignupRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("User already exists");
        }   

        Role role;

        if (request.getEmail().equalsIgnoreCase("admin@gmail.com")) {
            role = Role.ADMIN;
        } else {
            role = Role.USER;
        }

        User user = User.builder()
            .name(request.getName())
            .email(request.getEmail())
            .password(passwordEncoder.encode(request.getPassword()))
            .role(role)
            .build();

        return userRepository.save(user);
    }
}