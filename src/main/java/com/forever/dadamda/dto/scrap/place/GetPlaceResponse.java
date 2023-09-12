package com.forever.dadamda.dto.scrap.place;

import com.forever.dadamda.dto.memo.GetMemoResponse;
import com.forever.dadamda.entity.scrap.Place;
import java.math.BigDecimal;
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
public class GetPlaceResponse {

    // 공통 부분
    private Long scrapId;
    private String dType;
    private String description;
    private String pageUrl;
    private String siteName;
    private String thumbnailUrl;
    private String title;
    private List<GetMemoResponse> memoList;

    // Place 부분
    private String address;
    private BigDecimal latitude;
    private BigDecimal longitude;
    private String phoneNumber;
    private String zipCode;
    private String homepageUrl;
    private String category;

    public static GetPlaceResponse of(Place place) {
        return new GetPlaceResponseBuilder()
                .scrapId(place.getId())
                .dType("place")
                .description(place.getDescription())
                .pageUrl(place.getPageUrl())
                .siteName(place.getSiteName())
                .thumbnailUrl(place.getThumbnailUrl())
                .title(place.getTitle())
                .memoList(place.getMemoList().stream().map(GetMemoResponse::of).collect(
                        Collectors.toList()))
                .address(place.getAddress())
                .latitude(place.getLatitude())
                .longitude(place.getLongitude())
                .phoneNumber(place.getPhoneNumber())
                .zipCode(place.getZipCode())
                .homepageUrl(place.getHomepageUrl())
                .category(place.getCategory())
                .build();
    }
}
