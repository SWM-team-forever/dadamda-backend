package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.CreateScrapRequest;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.dto.scrap.GetScrapCountResponse;
import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.service.scrap.ScrapService;
import io.swagger.v3.oas.annotations.Operation;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import org.springframework.data.domain.Pageable;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RequiredArgsConstructor
@RestController
public class ScrapController {

    private final ScrapService scrapService;

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
    public ApiResponse<String> deleteScraps(@PathVariable("scrapId") @NotNull @Positive Long scrapId,
            Authentication authentication) {

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

    @Operation(summary = "전체 스크랩 수정", description = "스크랩을 수정할 수 있습니다.")
    @PatchMapping("/v1/scraps")
    public ApiResponse<String> updateScraps(
            @Valid @RequestBody UpdateScrapRequest updateScrapRequest,
            Authentication authentication) {

        String email = authentication.getName();
        scrapService.updateScraps(email, updateScrapRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "전체 스크랩 개수 조회", description = "전체 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/count")
    public ApiResponse<GetScrapCountResponse> getScrapCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetScrapCountResponse.of(scrapService.getScrapCount(email)));
    }

    @Operation(summary = "스크랩 검색", description = "스크랩을 검색할 수 있습니다.")
    @GetMapping("/v1/scraps/search")
    public ApiResponse<Slice<GetScrapResponse>> searchScraps(
            @RequestParam("keyword") @NotBlank String keyword,
            Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(scrapService.searchScraps(email, keyword, pageable));
    }
}
