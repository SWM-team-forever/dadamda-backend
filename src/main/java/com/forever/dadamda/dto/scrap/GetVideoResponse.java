package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.service.TimeService;
import com.forever.dadamda.service.scrap.VideoService;
import java.util.List;
import java.util.stream.Collectors;
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
    private String dType;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private List<GetMemoResponse> memoList;

    // Video 부분
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private String playTime;
    private String watchedCnt;
    private Long publishedDate;

    public static GetVideoResponse of(Video video) {
        return new GetVideoResponseBuilder()
                .scrapId(video.getId())
                .dType("video")
                .description(video.getDescription())
                .pageUrl(video.getPageUrl())
                .siteName(video.getSiteName())
                .thumbnailUrl(video.getThumbnailUrl())
                .title(video.getTitle())
                .channelImageUrl(video.getChannelImageUrl())
                .channelName(video.getChannelName())
                .embedUrl(video.getEmbedUrl())
                .playTime(VideoService.formatPlayTime(video.getPlayTime()))
                .watchedCnt(VideoService.formatViewCount(video.getWatchedCnt()))
                .memoList(video.getMemoList().stream().map(GetMemoResponse::of).collect(
                        Collectors.toList()))
                .publishedDate(TimeService.fromLocalDateTime(video.getPublishedDate()))
                .build();
    }
}
