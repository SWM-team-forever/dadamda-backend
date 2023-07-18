package com.forever.dadamda.dto.scrap;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class CreateScrapRequest {

    @NotBlank(message = "url을 입력해주세요.")
    @URL(message = "URL 형식이 유효하지 않습니다.")
    @Size(max = 2083, message = "최대 2083자까지 입력할 수 있습니다.")
    private String pageUrl;
}