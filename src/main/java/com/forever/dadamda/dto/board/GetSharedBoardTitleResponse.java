package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetSharedBoardTitleResponse {
    private String title;

    public static GetSharedBoardTitleResponse of(Board board) {
        return GetSharedBoardTitleResponse.builder()
                .title(board.getTitle())
                .build();
    }
}
