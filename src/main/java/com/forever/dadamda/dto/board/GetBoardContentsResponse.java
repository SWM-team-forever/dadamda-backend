package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardContentsResponse {

    private String contents;

    public static GetBoardContentsResponse of(Board board) {
        return GetBoardContentsResponse.builder()
                .contents(board.getContents())
                .build();
    }
}
