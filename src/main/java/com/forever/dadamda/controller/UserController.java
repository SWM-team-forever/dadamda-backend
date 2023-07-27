package com.forever.dadamda.controller;

import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.dto.user.LoginResponse;
import com.forever.dadamda.service.TokenService;
import com.forever.dadamda.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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

    @Operation(summary = "회원 정보 조회", description = "해당 회원의 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/user")
    public GetUserInfoResponse getUserInfo(Authentication authentication) {
        String email = authentication.getName();

        return userService.getUserInfo(email);
    }

}
