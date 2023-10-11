package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardDetailResponse {

    private Long boardId;
    private String title;
    private String description;
    private TAG tag;

    public static GetBoardDetailResponse of(Board board) {
        return GetBoardDetailResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .description(board.getDescription())
                .tag(board.getTag())
                .build();
    }
}
