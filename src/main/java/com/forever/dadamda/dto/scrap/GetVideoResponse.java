package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.scrap.Video;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetVideoResponse {

    // 공통 부분
    private Long scrapId;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;

    // Video 부분
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private String genre;
    private Long playTime;
    private Long watchedCnt;

    public static GetVideoResponse of(Video video) {
        return new GetVideoResponseBuilder()
                .scrapId(video.getId())
                .description(video.getDescription())
                .pageUrl(video.getPageUrl())
                .siteName(video.getSiteName())
                .thumbnailUrl(video.getThumbnailUrl())
                .title(video.getTitle())
                .channelImageUrl(video.getChannelImageUrl())
                .channelName(video.getChannelName())
                .embedUrl(video.getEmbedUrl())
                .genre(video.getGenre())
                .playTime(video.getPlayTime())
                .watchedCnt(video.getWatchedCnt())
                .build();
    }
}
