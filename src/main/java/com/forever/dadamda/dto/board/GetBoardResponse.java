package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.service.TimeService;
import java.time.LocalDateTime;
import java.util.UUID;
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
    private String title;
    private LocalDateTime isFixed;
    private UUID uuid;
    private TAG tag;
    private Long modifiedDate;
    private String thumbnailUrl;

    public static GetBoardResponse of(Board board) {
        return GetBoardResponse.builder()
                .boardId(board.getId())
                .title(board.getTitle())
                .isFixed(board.getFixedDate())
                .uuid(board.getUuid())
                .tag(board.getTag())
                .modifiedDate(TimeService.fromLocalDateTime(board.getModifiedDate()))
                .thumbnailUrl(board.getThumbnailUrl())
                .build();
    }
}
