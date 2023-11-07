package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.user.GetProfileUrlResponse;
import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.dto.user.UpdateNicknameRequest;
import com.forever.dadamda.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
public class UserController {

    private final UserService userService;

    @GetMapping("/oauth-login")
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


    @Operation(summary = "회원 프로필 이미지 업로드", description = "해당 회원 프로필 이미지를 업로드할 수 있습니다.")
    @PostMapping("/v1/user/profile/image")
    public ApiResponse uploadProfileImage(Authentication authentication, @RequestParam("file") MultipartFile file) {
        String email = authentication.getName();
        userService.uploadProfileImage(email, file);

        return ApiResponse.success();
    }

    @Operation(summary = "회원 프로필 이미지 삭제", description = "해당 회원 프로필 이미지를 삭제할 수 있습니다.")
    @DeleteMapping("/v1/user/profile/image")
    public ApiResponse deleteProfileImage(Authentication authentication) {
        String email = authentication.getName();
        userService.deleteProfileImage(email);

        return ApiResponse.success();
    }

    @Operation(summary = "회원 닉네임 수정", description = "해당 회원 닉네임를 수정할 수 있습니다.")
    @PatchMapping("/v1/user/profile/nickname")
    public ApiResponse<String> updateNickname(
            @Valid @RequestBody UpdateNicknameRequest updateNicknameRequest,
            Authentication authentication) {
        String email = authentication.getName();

        userService.updateNickname(updateNicknameRequest.getNickname(), email);

        return ApiResponse.success();
    }
}
