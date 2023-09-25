package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성", description = "1개의 보드를 생성합니다.")
    @PostMapping("/v1/boards")
    public ApiResponse<String> createBoards(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.createBoards(email, createBoardRequest);
        return ApiResponse.success();
    }
}
