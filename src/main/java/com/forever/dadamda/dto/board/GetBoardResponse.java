package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.service.TimeService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardResponse {

    private Long boardId;
    private String title;
    private LocalDateTime isFixed;
    private UUID uuid;
    private TAG tag;
    private Long modifiedDate;
    private String thumbnailUrl;
    private String contents;

    public static GetBoardResponse of(Board board) {
        return GetBoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .isFixed(board.getFixedDate())
                .uuid(board.getUuid())
                .tag(board.getTag())
                .modifiedDate(TimeService.fromLocalDateTime(board.getModifiedDate()))
                .thumbnailUrl(board.getThumbnailUrl())
                .contents(board.getContents())
                .build();
    }
}
