package com.forever.dadamda.dto.board;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBoardContentsRequest {

    private String contents;
}
