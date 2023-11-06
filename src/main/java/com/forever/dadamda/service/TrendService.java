package com.forever.dadamda.service;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.trend.GetTrendBoardResponse;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.heart.Heart;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.repository.HeartRepository;
import com.forever.dadamda.service.user.UserService;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class TrendService {

    private final UserService userService;
    private final HeartRepository heartRepository;
    private final BoardRepository boardRepository;

    @Transactional
    public void addHearts(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.addHeartCnt();

        Heart heart = Heart.builder()
                .user(user)
                .board(board)
                .build();

        heartRepository.save(heart);
    }

    @Transactional
    public void deleteHearts(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        if(board.getHeartCnt() > 0) {
            board.deleteHeartCnt(board.getHeartCnt()-1);
        } else {
            throw new InvalidException(ErrorCode.INVALID);
        }

        Heart heart = heartRepository.findByUserAndBoardAndDeletedDateIsNull(user, board)
                .orElseThrow(() -> new NotFoundException(ErrorCode.INVALID_HEART));

        heart.updateDeletedDate(LocalDateTime.now());
    }

    @Transactional(readOnly = true)
    public Slice<GetTrendBoardResponse> getTrendBoardList(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Pageable pageable, String tag) {

        return boardRepository.getTrendBoardListOrderByHeartCnt(trendStartDateTime,
                        trendEndDateTime, pageable, tag)
                .map(GetTrendBoardResponse::of);
    }

    public void updateViewCnt(UUID boardUUID) {
        Board board = boardRepository.findByUuidAndDeletedDateIsNull(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.addViewCnt();
    }
}
