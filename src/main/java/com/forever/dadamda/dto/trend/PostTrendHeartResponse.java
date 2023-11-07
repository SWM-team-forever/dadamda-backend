package com.forever.dadamda.dto.trend;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class PostTrendHeartResponse {

    private Boolean isHeart; // 하트 여부 (true: 하트 추가, false: 하트 취소)
}
