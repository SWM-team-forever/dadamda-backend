package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.place.GetPlaceCountResponse;
import com.forever.dadamda.dto.scrap.place.GetPlaceResponse;
import com.forever.dadamda.service.scrap.PlaceService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class PlaceController {

    private final PlaceService placeService;

    @Operation(summary = "장소 조회", description = "여러 개의 장소를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/places")
    public ApiResponse<Slice<GetPlaceResponse>> getPlaces(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(placeService.getPlaces(email, pageable));
    }

    @Operation(summary = "장소 스크랩 개수 조회", description = "장소 스크랩 개수를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/places/count")
    public ApiResponse<GetPlaceCountResponse> getOtherScrap(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetPlaceCountResponse.of(placeService.getPlaceCount(email)));
    }
}
