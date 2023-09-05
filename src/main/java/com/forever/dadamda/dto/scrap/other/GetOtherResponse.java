package com.forever.dadamda.dto.scrap.other;

import com.forever.dadamda.dto.memo.GetMemoResponse;
import com.forever.dadamda.entity.scrap.Other;
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
public class GetOtherResponse {

    // 공통 부분
    private Long scrapId;
    private String dType;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private List<GetMemoResponse> memoList;

    public static GetOtherResponse of(Other other) {
        return new GetOtherResponseBuilder()
                .scrapId(other.getId())
                .dType("other")
                .description(other.getDescription())
                .pageUrl(other.getPageUrl())
                .siteName(other.getSiteName())
                .thumbnailUrl(other.getThumbnailUrl())
                .title(other.getTitle())
                .memoList(other.getMemoList().stream().map(GetMemoResponse::of).collect(
                        Collectors.toList()))
                .build();
    }
}
