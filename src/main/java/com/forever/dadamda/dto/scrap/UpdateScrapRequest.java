package com.forever.dadamda.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateScrapRequest {
    // 공통 부분
    private Long scrapId;

    @JsonProperty("dType")
    private String dType;

    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private List<GetMemoResponse> memoList;

    // Article 부분
    private String author;
    private String authorImageUrl;
    private String blogName;
    private LocalDateTime publishedDate;

    // Product 부분
    private String price;

    // Video 부분
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private String genre;
    private Long playTime;
    private Long watchedCnt;
}
