package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSharedBoardContentsResponse {

    private String contents;

    public static GetSharedBoardContentsResponse of(Board board) {
        return GetSharedBoardContentsResponse.builder()
                .contents(board.getContents())
                .build();
    }
}
