package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.article.GetArticleCountResponse;
import com.forever.dadamda.dto.scrap.article.GetArticleResponse;
import com.forever.dadamda.service.scrap.ArticleService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class ArticleController {

    private final ArticleService articleService;

    @Operation(summary = "아티클 조회", description = "여러개의 아티클을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/articles")
    public ApiResponse<Slice<GetArticleResponse>> getArticles(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(articleService.getArticles(email, pageable));
    }

    @Operation(summary = "아티클 스크랩 개수 조회", description = "아티클 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/articles/count")
    public ApiResponse<GetArticleCountResponse> getArticleCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(
                GetArticleCountResponse.of(articleService.getArticleCount(email)));
    }

    @Operation(summary = "아티클 스크랩 검색", description = "타이틀과 설명의 키워드로 아티클 스크랩을 검색할 수 있습니다.")
    @GetMapping("/v1/scraps/articles/search")
    public ApiResponse<Slice<GetArticleResponse>> searchArticles(
            @RequestParam("keyword") String keyword,
            Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(articleService.searchArticles(email, keyword, pageable));
    }
}
