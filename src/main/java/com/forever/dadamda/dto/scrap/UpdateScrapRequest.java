package com.forever.dadamda.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("dtype")
    private String dType;

    private String description;
    private String siteName;
    private String title;

    // Article 부분
    private String author;
    private String blogName;

    // Product 부분
    private String price;

    // Video 부분
    private String channelName;
}
