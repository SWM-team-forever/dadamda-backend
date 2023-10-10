package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.service.TimeService;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class GetBoardResponse {

    private Long boardId;
    private String boardName;
    private String description;
    private LocalDateTime isFixed;
    private TAG tag;
    private Long modifiedDate;

    public static GetBoardResponse of(Board board) {
        return GetBoardResponse.builder()
                .boardId(board.getId())
                .boardName(board.getName())
                .description(board.getDescription())
                .isFixed(board.getFixedDate())
                .tag(board.getTag())
                .modifiedDate(TimeService.fromLocalDateTime(board.getModifiedDate()))
                .build();
    }
}
