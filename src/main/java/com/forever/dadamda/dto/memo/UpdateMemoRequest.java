package com.forever.dadamda.dto.memo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class UpdateMemoRequest {

    @NotNull
    @Positive
    private Long scrapId;

    @NotNull
    @Positive(message = "memoId는 1보다 커야 합니다.")
    private Long memoId;

    @NotBlank
    private String memoText;
}
