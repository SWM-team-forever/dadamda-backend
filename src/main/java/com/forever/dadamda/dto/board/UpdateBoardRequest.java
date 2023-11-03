package com.forever.dadamda.dto.board;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class UpdateBoardRequest {

    @NotBlank(message = "보드명을 입력해주세요.")
    @Size(max = 100, message = "최대 100자까지 입력할 수 있습니다.")
    private String title;

    @Size(max = 1000, message = "최대 1000자까지 입력할 수 있습니다.")
    private String description;

    @NotBlank(message = "태그를 입력해주세요.")
    private String tag;
}
