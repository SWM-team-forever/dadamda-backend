package com.forever.dadamda.dto.memo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class DeleteMemoRequest {

    @NotNull
    @Positive(message = "scrapId는 1보다 커야 합니다.")
    private Long scrapId;

    @NotNull
    @Positive(message = "memoId는 1보다 커야 합니다.")
    private Long memoId;
}
