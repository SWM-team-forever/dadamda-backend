package com.forever.dadamda.controller;

import com.forever.dadamda.dto.user.LoginResponse;
import com.forever.dadamda.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {
    private final TokenService tokenService;

    @GetMapping("")
    public LoginResponse loginResponse(@RequestParam String token) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        String email = tokenService.getEmail(token);
        return LoginResponse.of(email);
    }
}
