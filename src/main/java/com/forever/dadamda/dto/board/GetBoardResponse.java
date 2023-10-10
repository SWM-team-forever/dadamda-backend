package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardResponse {

    private Long boardId;
    private String boardName;
    private String description;
    private TAG tag;

    public static GetBoardResponse of(Board board) {
        return GetBoardResponse.builder()
                .boardId(board.getId())
                .boardName(board.getName())
                .description(board.getDescription())
                .tag(board.getTag())
                .build();
    }
}
