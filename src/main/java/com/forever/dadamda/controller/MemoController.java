package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.CreateHighlightRequest;
import com.forever.dadamda.dto.scrap.CreateHighlightResponse;
import com.forever.dadamda.dto.scrap.CreateMemoRequest;
import com.forever.dadamda.service.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @Operation(summary = "메모 추가", description = "크롬 익스텐션을 사용하여, 하이라이트할 문자들과 사진을 추가할 수 있습니다.")
    @PostMapping("/v1/scraps/memo")
    public ApiResponse addMemo(@Valid @RequestBody CreateMemoRequest createMemoRequest,
            Authentication authentication) {
        String email = authentication.getName();
        memoService.createMemo(email, createMemoRequest);
        return ApiResponse.success();
    }
}
