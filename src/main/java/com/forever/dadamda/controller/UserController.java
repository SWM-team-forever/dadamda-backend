package com.forever.dadamda.controller;

import com.forever.dadamda.dto.user.LoginResponse;
import com.forever.dadamda.service.TokenService;
import com.forever.dadamda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final TokenService tokenService;
    private final UserService userService;

    @GetMapping("")
    public LoginResponse loginResponse(@RequestParam String token) {

        String profileUrl = userService.getProfileUrl(tokenService.getEmail(token));

        return LoginResponse.of(profileUrl);
    }
}
