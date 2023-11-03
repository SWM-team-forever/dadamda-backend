package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.service.TrendService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class TrendController {

    private final TrendService trendService;

    @Operation(summary = "하트 누르기", description = "트랜딩 보드의 하트를 누를 수 있습니다.")
    @PostMapping("/v1/trends/heart/{boardUUID}")
    public ApiResponse<String> addHearts(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {

        String email = authentication.getName();
        trendService.addHearts(email, UUID.fromString(boardUUID));

        return ApiResponse.success();
    }
}
