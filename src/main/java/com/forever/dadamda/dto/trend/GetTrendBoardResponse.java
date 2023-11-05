package com.forever.dadamda.dto.trend;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.service.TimeService;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetTrendBoardResponse {

    private String profileUrl; // 보드 작성자 프로필 사진
    private String nickname;    // 보드 작성자 닉네임
    private String title;   // 보드 제목
    private String description; // 보드 설명
    private TAG tag;    // 보드 태그
    private UUID uuid;  // 보드 UUID
    private Long heartCnt;  // 보드 하트 개수
    private Long shareCnt;    // 보드 공유 개수
    private Long viewCnt;   // 보드 조회수
    private Long createdAt; // 보드 생성일
    private String thumbnailUrl; // 보드 썸네일 사진
    private String contents; // 보드 내용

    public static GetTrendBoardResponse of(Board board) {
        return GetTrendBoardResponse.builder()
                .profileUrl(board.getUser().getProfileUrl())
                .nickname(board.getUser().getNickname())
                .title(board.getTitle())
                .description(board.getDescription())
                .tag(board.getTag())
                .uuid(board.getUuid())
                .heartCnt(board.getHeartCnt())
                .shareCnt(board.getShareCnt())
                .viewCnt(board.getViewCnt())
                .createdAt(TimeService.fromLocalDateTime(board.getCreatedDate()))
                .thumbnailUrl(board.getThumbnailUrl())
                .contents(board.getContents())
                .build();
    }
}
