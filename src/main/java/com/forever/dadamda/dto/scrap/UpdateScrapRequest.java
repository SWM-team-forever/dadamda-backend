package com.forever.dadamda.dto.scrap;

import com.fasterxml.jackson.annotation.JsonProperty;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
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
    @NotNull(message = "scrapId를 입력해주세요.")
    @Positive(message = "scrapId는 1보다 커야 합니다.")
    private Long scrapId;

    @NotBlank(message = "dType을 입력해주세요.")
    @JsonProperty("dtype")
    private String dType;

    @Size(max = 1000, message = "최대 1000자까지 입력할 수 있습니다.")
    private String description;

    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String siteName;

    @Size(max = 200, message = "최대 200자까지 입력할 수 있습니다.")
    private String title;

    // Article 부분
    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String author;

    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String blogName;

    // Product 부분
    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String price;

    // Video 부분
    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String channelName;
}
