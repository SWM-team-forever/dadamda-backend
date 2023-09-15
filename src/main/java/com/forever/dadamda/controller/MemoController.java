package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.memo.CreateHighlightRequest;
import com.forever.dadamda.dto.memo.CreateHighlightResponse;
import com.forever.dadamda.dto.memo.CreateMemoRequest;
import com.forever.dadamda.dto.memo.DeleteMemoRequest;
import com.forever.dadamda.service.MemoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequiredArgsConstructor
@RestController
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

    @Operation(summary = "하이라이트 추가", description = "크롬 익스텐션을 사용하여, 하이라이트할 문자들과 사진을 추가할 수 있습니다.")
    @PostMapping("/v1/scraps/highlights")
    public ApiResponse<CreateHighlightResponse> addHighlights(
            @Valid @RequestBody CreateHighlightRequest createHighlightRequest) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        CreateHighlightResponse createHighlightResponse = memoService.createHighlights(email,
                createHighlightRequest);
        return ApiResponse.success(createHighlightResponse);
    }

    @Operation(summary = "1개 메모 삭제", description = "단 건의 메모를 삭제합니다.")
    @DeleteMapping("/v1/scraps/memo")
    public ApiResponse deleteMemo(
            @Valid @RequestBody DeleteMemoRequest deleteMemoRequest,
            Authentication authentication) {
        String email = authentication.getName();
        memoService.deleteMemo(email, deleteMemoRequest);
        return ApiResponse.success();
    }
}
