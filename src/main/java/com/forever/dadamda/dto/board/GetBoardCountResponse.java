package com.forever.dadamda.dto.board;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(staticName = "of")
public class GetBoardCountResponse {

    private Long count;
}
