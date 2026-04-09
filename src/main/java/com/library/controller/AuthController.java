package com.library.controller;

import com.library.dto.SignupRequest;
import com.library.model.User;
import com.library.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

    @PostMapping("/signup")
    public User signup(@RequestBody SignupRequest request) {
        return userService.registerUser(request);
    }
}