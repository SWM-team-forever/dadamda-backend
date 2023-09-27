package com.forever.dadamda.dto.memo;

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
public class UpdateMemoRequest {

    @NotNull
    @Positive(message = "scrapId는 1보다 커야 합니다.")
    private Long scrapId;

    @NotNull
    @Positive(message = "memoId는 1보다 커야 합니다.")
    private Long memoId;

    @NotBlank(message = "메모를 입력해주세요.")
    @Size(max = 1000, message = "최대 1000자까지 입력할 수 있습니다.")
    private String memoText;
}
