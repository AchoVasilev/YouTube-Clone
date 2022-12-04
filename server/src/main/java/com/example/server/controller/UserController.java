package com.example.server.controller;

import com.example.server.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/register")
    public String register(Authentication authentication) {
        var jwt = (Jwt) authentication.getPrincipal();

        this.userService.registerUser(jwt.getTokenValue());

        return "Successful registration";
    }

    @PostMapping("/{userId}/subscribe")
    public void subscribeToUser(@PathVariable("userId") String userId) {
        this.userService.subscribeToUser(userId);
    }

    @PostMapping("/{userId}/unsubscribe")
    public void unsubscribeFromUser(@PathVariable("userId") String userId) {
        this.userService.unsubscribeFromUser(userId);
    }
}
