package com.forever.dadamda.controller.scrap;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.scrap.video.GetVideoCountResponse;
import com.forever.dadamda.dto.scrap.video.GetVideoResponse;
import com.forever.dadamda.service.scrap.VideoService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class VideoController {

    private final VideoService videoService;

    @Operation(summary = "영상 조회", description = "여러개의 영상을 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/videos")
    public ApiResponse<Slice<GetVideoResponse>> getVideos(Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(videoService.getVideos(email, pageable));
    }

    @Operation(summary = "비디오 스크랩 개수 조회", description = "비디오 스크랩 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/scraps/videos/count")
    public ApiResponse<GetVideoCountResponse> getVideoCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetVideoCountResponse.of(videoService.getVideoCount(email)));
    }
}
