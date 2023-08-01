package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.user.GetProfileUrlResponse;
import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("")
    public ApiResponse<String> login(@RequestParam String token) {
        return ApiResponse.success();
    }

    @Operation(summary = "회원 정보 조회", description = "해당 회원의 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/user")
    public ApiResponse<GetUserInfoResponse> getUserInfo(Authentication authentication) {
        String email = authentication.getName();

        return ApiResponse.success(userService.getUserInfo(email));
    }

    @Operation(summary = "회원 탈퇴", description = "해당 회원 탈퇴할 수 있습니다.")
    @DeleteMapping("/v1/user")
    public ApiResponse<String> deleteUser(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteUser(email);

        return ApiResponse.success();
    }

    @Operation(summary = "회원 프로필 이미지 조회", description = "해당 회원 프로필 이미지를 조회할 수 있습니다.")
    @GetMapping("/v1/user/profile")
    public ApiResponse<GetProfileUrlResponse> getProfileUrl(Authentication authentication) {
        String email = authentication.getName();
        GetProfileUrlResponse getProfileUrlResponse = new GetProfileUrlResponse(
                userService.getProfileUrl(email));
        return ApiResponse.success(getProfileUrlResponse);
    }
}
