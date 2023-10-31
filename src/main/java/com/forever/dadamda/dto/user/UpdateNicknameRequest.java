package com.forever.dadamda.dto.user;

import javax.validation.constraints.NotBlank;
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
public class UpdateNicknameRequest {

    @NotBlank(message = "닉네임을 입력해주세요.")
    @Size(max = 10, message = "최대 10자까지 입력할 수 있습니다.")
    private String nickname;

}
