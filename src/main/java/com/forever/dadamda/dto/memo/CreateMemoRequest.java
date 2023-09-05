package com.forever.dadamda.dto.memo;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Size;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateMemoRequest {

    private Long scrapId;

    @Size(max = 1000, message = "최대 1000자까지 선택할 수 있습니다.")
    private String memoText;
}
