package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.CreateScrapRequest;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.dto.scrap.GetArticleCountResponse;
import com.forever.dadamda.dto.scrap.GetArticleResponse;
import com.forever.dadamda.dto.scrap.GetOtherCountResponse;
import com.forever.dadamda.dto.scrap.GetOtherResponse;
import com.forever.dadamda.dto.scrap.GetProductCountResponse;
import com.forever.dadamda.dto.scrap.GetProductResponse;
import com.forever.dadamda.dto.scrap.GetScrapCountResponse;
import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.dto.scrap.GetVideoCountResponse;
import com.forever.dadamda.dto.scrap.GetVideoResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.service.MemoService;
import com.forever.dadamda.service.scrap.ArticleService;
import com.forever.dadamda.service.scrap.OtherService;
import com.forever.dadamda.service.scrap.ProductService;
import com.forever.dadamda.service.scrap.ScrapService;
import com.forever.dadamda.service.scrap.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final ScrapService scrapService;
    private final MemoService memoService;
    private final ProductService productService;
    private final VideoService videoService;
    private final ArticleService articleService;
    private final OtherService otherService;

    @Operation(summary = "스크랩 추가", description = "'크롬 익스텐션'과 '+ 버튼'을 통해서 스크랩을 추가할 수 있습니다.")
    @PostMapping("/v1/scraps")
    public ApiResponse<CreateScrapResponse> addScraps(
            @Valid @RequestBody CreateScrapRequest createScrapRequest,
            Authentication authentication) throws ParseException {

        String email = authentication.getName();

        CreateScrapResponse createScrapResponse = scrapService.createScraps(email,
                createScrapRequest.getPageUrl());
        return ApiResponse.success(createScrapResponse);
    }

    @Operation(summary = "스크랩 삭제", description = "한개의 스크랩을 삭제할 수 있습니다.")
    @DeleteMapping("/v1/scraps/{scrapId}")
    public ApiResponse<String> deleteScraps(@Valid @PathVariable("scrapId") Long scrapId,
            Authentication authentication) {

        String email = authentication.getName();

        scrapService.deleteScraps(email, scrapId);

        return ApiResponse.success();
    }

    @Operation(summary = "스크랩 조회", description = "여러 개의 스크랩을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps")
    public ApiResponse<Slice<GetScrapResponse>> getScraps(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getScraps(email, pageable));
    }

    @Operation(summary = "스크랩 단 건 조회", description = "한 개의 스크랩을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/{scrapId}")
    public ApiResponse<GetScrapResponse> getScrap(@PathVariable("scrapId") Long scrapId,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.getScrap(email, scrapId));
    }

    @Operation(summary = "상품 조회", description = "여러개의 상품을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/products")
    public ApiResponse<Slice<GetProductResponse>> getProducts(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(productService.getProducts(email, pageable));
    }

    @Operation(summary = "영상 조회", description = "여러개의 영상을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/videos")
    public ApiResponse<Slice<GetVideoResponse>> getVideos(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(videoService.getVideos(email, pageable));
    }

    @Operation(summary = "아티클 조회", description = "여러개의 아티클을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/articles")
    public ApiResponse<Slice<GetArticleResponse>> getArticles(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(articleService.getArticles(email, pageable));
    }

    @Operation(summary = "기타 스크랩 조회", description = "여러개의 기타 스크랩를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/others")
    public ApiResponse<Slice<GetOtherResponse>> getOthers(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(otherService.getOthers(email, pageable));
    }

    @Operation(summary = "전체 스크랩 수정", description = "스크랩을 수정할 수 있습니다.")
    @PatchMapping("/v1/scraps")
    public ApiResponse<String> updateScraps(
            @RequestBody UpdateScrapRequest updateScrapRequest,
            Authentication authentication) {

        String email = authentication.getName();
        Scrap scrap = scrapService.updateScraps(email, updateScrapRequest);
        memoService.updateMemos(scrap, updateScrapRequest.getMemoList());
        return ApiResponse.success();
    }

    @Operation(summary = "전체 스크랩 개수 조회", description = "전체 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/count")
    public ApiResponse<GetScrapCountResponse> getScrapCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetScrapCountResponse.of(scrapService.getScrapCount(email)));
    }

    @Operation(summary = "아티클 스크랩 개수 조회", description = "아티클 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/articles/count")
    public ApiResponse<GetArticleCountResponse> getArticleCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(
                GetArticleCountResponse.of(articleService.getArticleCount(email)));
    }

    @Operation(summary = "기타 스크랩 개수 조회", description = "기타 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/others/count")
    public ApiResponse<GetOtherCountResponse> getOtherScrap(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetOtherCountResponse.of(otherService.getOtherCount(email)));
    }

    @Operation(summary = "상품 스크랩 개수 조회", description = "상품 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/products/count")
    public ApiResponse<GetProductCountResponse> getProductScrap(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(
                GetProductCountResponse.of(productService.getProductCount(email)));
    }

    @Operation(summary = "비디오 스크랩 개수 조회", description = "비디오 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/videos/count")
    public ApiResponse<GetVideoCountResponse> getVideoCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetVideoCountResponse.of(videoService.getVideoCount(email)));
    }
}
