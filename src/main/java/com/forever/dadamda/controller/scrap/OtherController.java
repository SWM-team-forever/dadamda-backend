package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.other.GetOtherCountResponse;
import com.forever.dadamda.dto.scrap.other.GetOtherResponse;
import com.forever.dadamda.service.scrap.OtherService;
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
public class OtherController {

    private final OtherService otherService;

    @Operation(summary = "기타 스크랩 조회", description = "여러개의 기타 스크랩를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/others")
    public ApiResponse<Slice<GetOtherResponse>> getOthers(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(otherService.getOthers(email, pageable));
    }

    @Operation(summary = "기타 스크랩 개수 조회", description = "기타 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/others/count")
    public ApiResponse<GetOtherCountResponse> getOtherCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetOtherCountResponse.of(otherService.getOtherCount(email)));
    }

    @Operation(summary = "기타 스크랩 검색", description = "타이틀과 설명의 키워드로 기타 스크랩을 검색할 수 있습니다.")
    @GetMapping("/v1/scraps/others/search")
    public ApiResponse<Slice<GetOtherResponse>> searchArticles(
            @RequestParam("keyword") String keyword,
            Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(otherService.searchOthers(email, keyword, pageable));
    }
}
