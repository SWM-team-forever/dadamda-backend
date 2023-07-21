package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.CreateHighlightRequest;
import com.forever.dadamda.dto.scrap.CreateHighlightResponse;
import com.forever.dadamda.dto.scrap.CreateScrapRequest;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.service.MemoService;
import com.forever.dadamda.service.scrap.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.Slice;
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

    @Operation(summary = "스크랩 조회", description = "여러개의 스크랩을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps")
    public ApiResponse<Slice<GetScrapResponse>> getScraps(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getScraps(email, pageable));
    }

    @Operation(summary = "상품 조회", description = "여러개의 상품을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/products")
    public ApiResponse<Slice<GetScrapResponse>> getProducts(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getProducts(email, pageable));
    }

    @Operation(summary = "영상 조회", description = "여러개의 영상을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/videos")
    public ApiResponse<Slice<GetScrapResponse>> getVideos(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getVideos(email, pageable));
    }

    @Operation(summary = "아티클 조회", description = "여러개의 아티클을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/articles")
    public ApiResponse<Slice<GetScrapResponse>> getArticles(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getArticles(email, pageable));
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
