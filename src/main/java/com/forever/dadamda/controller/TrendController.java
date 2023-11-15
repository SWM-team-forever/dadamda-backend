package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.trend.GetPopularUsersResponse;
import com.forever.dadamda.dto.trend.GetTrendBoardResponse;
import com.forever.dadamda.dto.trend.PostTrendHeartResponse;
import com.forever.dadamda.service.TrendService;
import io.swagger.v3.oas.annotations.Operation;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TrendController {

    private final TrendService trendService;

    @Operation(summary = "하트 누르기", description = "트랜딩 보드의 하트를 누를 수 있습니다.")
    @PostMapping("/v1/trends/heart/{boardUUID}")
    public ApiResponse<PostTrendHeartResponse> updateHearts(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(PostTrendHeartResponse.of(
                trendService.updateHearts(email, UUID.fromString(boardUUID))));
    }

    @Operation(summary = "트랜드 보드 조회하기", description = "트랜딩 보드를 조회할 수 있습니다.")
    @GetMapping("/ov1/trends/boards")
    public ApiResponse<Slice<GetTrendBoardResponse>> getTrendBoardList(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            Pageable pageable, String tag) {

        return ApiResponse.success(
                trendService.getTrendBoardList(startDate, endDate, pageable, tag));
    }

    @Operation(summary = "보드 조회수 증가", description = "보드 조회수를 증가시킵니다.")
    @PatchMapping("/ov1/trends/boards/{boardUUID}")
    public ApiResponse<String> updateViewCnt(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID) {

        trendService.updateViewCnt(UUID.fromString(boardUUID));

        return ApiResponse.success();
    }

    @Operation(summary = "트렌딩 인기 유저 조회", description = "트렌딩 인기 유저를 조회합니다.")
    @GetMapping("/ov1/trends/popularUsers")
    public ApiResponse<List<GetPopularUsersResponse>> getPopularUsers(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            Long limit) {

        return ApiResponse.success(trendService.getPopularUsers(startDate, endDate, limit));
    }

    @Operation(summary = "트렌딩 보드 검색", description = "트렌딩에서 보드명을 검색할 수 있습니다.")
    @GetMapping("/ov1/trends/search")
    public ApiResponse<Slice<GetTrendBoardResponse>> searchTrendBoards(
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startDate,
            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endDate,
            @RequestParam("keyword") @NotBlank String keyword,
            Pageable pageable) {

        return ApiResponse.success(trendService.searchTrendBoards(startDate, endDate, keyword, pageable));
    }
}
