package com.forever.dadamda.dto.webClient;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import java.math.BigDecimal;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
@NoArgsConstructor
public class WebClientBodyResponse {

    // 공통 부분
    private String type;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;

    // Video 부분
    private String channelImageUrl;
    private String channelName;
    private String embedUrl;
    private Long playTime;
    private Long watchedCnt;
    private Long publishedDate; // Video, Article 공통 부분

    // Article 부분
    private String author;
    private String authorImageUrl;
    private String blogName;

    // Product 부분
    private String price;

    // Place 부분
    private String address;

    @JsonProperty("lat")
    private BigDecimal latitude;

    @JsonProperty("lng")
    private BigDecimal longitude;

    @JsonProperty("phonenum")
    private String phoneNumber;
    private String zipCode;

    @JsonProperty("homepage")
    private String homepageUrl;
    private String category;

    @Builder
    public WebClientBodyResponse(String type, String pageUrl, String title, String address, BigDecimal latitude,
            BigDecimal longitude, String phoneNumber, Long publishedDate) {
        this.type = type;
        this.pageUrl = pageUrl;
        this.address = address;
        this.title = title;
        this.latitude = latitude;
        this.longitude = longitude;
        this.phoneNumber = phoneNumber;
        this.publishedDate = publishedDate;
    }
}
