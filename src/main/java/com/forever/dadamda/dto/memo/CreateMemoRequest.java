package com.forever.dadamda.dto.memo;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemoRequest {

    @NotNull(message = "scrapId를 입력해주세요.")
    @Positive(message = "scrapId는 1보다 커야 합니다.")
    private Long scrapId;

    @NotBlank(message = "메모를 입력해주세요.")
    @Size(max = 1000, message = "최대 1000자까지 선택할 수 있습니다.")
    private String memoText;
}
