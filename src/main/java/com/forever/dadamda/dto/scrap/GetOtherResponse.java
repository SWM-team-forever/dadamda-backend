package com.forever.dadamda.dto.scrap;

import com.forever.dadamda.entity.scrap.Other;
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
public class GetOtherResponse {

    // 공통 부분
    private Long scrapId;
    private String dType;
    private LocalDateTime createdDate;
    private LocalDateTime modifiedDate;
    private LocalDateTime deletedDate;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;

    public static GetOtherResponse of(Other other) {
        return new GetOtherResponseBuilder()
                .scrapId(other.getId())
                .createdDate(other.getCreatedDate())
                .modifiedDate(other.getModifiedDate())
                .deletedDate(other.getDeletedDate())
                .description(other.getDescription())
                .pageUrl(other.getPageUrl())
                .siteName(other.getSiteName())
                .thumbnailUrl(other.getThumbnailUrl())
                .deletedDate(other.getDeletedDate())
                .title(other.getTitle())
                .dType("other")
                .build();
    }
}
