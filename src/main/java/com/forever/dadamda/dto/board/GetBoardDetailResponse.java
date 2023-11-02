package com.forever.dadamda.dto.board;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.service.TimeService;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardDetailResponse {

    private Long boardId;
    private String boardUUID;
    private String title;
    private boolean isPublic;
    private boolean isShared;
    private Long fixedDate;
    private String description;
    private TAG tag;
    private Long heartCnt;
    private Long viewCnt;
    private Long shareCnt;
    private String thumbnailUrl;

    public static GetBoardDetailResponse of(Board board) {
        return GetBoardDetailResponse.builder()
                .boardId(board.getId())
                .boardUUID(board.getUuid().toString())
                .title(board.getTitle())
                .isPublic(board.isPublic())
                .isShared(board.isShared())
                .fixedDate(TimeService.fromLocalDateTime(board.getFixedDate()))
                .description(board.getDescription())
                .tag(board.getTag())
                .heartCnt(board.getHeartCnt())
                .viewCnt(board.getViewCnt())
                .shareCnt(board.getShareCnt())
                .thumbnailUrl(board.getThumbnailUrl())
                .build();
    }
}
