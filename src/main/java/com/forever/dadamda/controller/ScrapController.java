package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.CreateHighlightRequest;
import com.forever.dadamda.dto.scrap.CreateHighlightResponse;
import com.forever.dadamda.dto.scrap.CreateScrapRequest;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.service.MemoService;
import com.forever.dadamda.service.scrap.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final ScrapService scrapService;
    private final MemoService memoService;

    @Operation(summary = "스크랩 추가", description = "'크롬 익스텐션'과 '+ 버튼'을 통해서 스크랩을 추가할 수 있습니다.")
    @PostMapping("/v1/scraps")
    public ApiResponse<CreateScrapResponse> addScraps(
            @Valid @RequestBody CreateScrapRequest createScrapRequest) throws ParseException {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        CreateScrapResponse createScrapResponse = scrapService.createScraps(email,
                createScrapRequest.getPageUrl());
        return ApiResponse.success(createScrapResponse);
    }

    @Operation(summary = "스크랩 삭제", description = "한개의 스크랩을 삭제할 수 있습니다.")
    @DeleteMapping("/v1/scraps/{scrapId}")
    public ApiResponse<String> deleteScraps(
            @Valid @PathVariable("scrapId") Long scrapId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();

        scrapService.deleteScraps(email, scrapId);

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
}
