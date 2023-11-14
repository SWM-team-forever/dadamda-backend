package com.forever.dadamda.service;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.trend.GetPopularUsersResponse;
import com.forever.dadamda.dto.trend.GetTrendBoardResponse;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.heart.Heart;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.repository.HeartRepository;
import com.forever.dadamda.service.user.UserService;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
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
    public Boolean updateHearts(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        Optional<Heart> heart = heartRepository.findByUserAndBoardAndDeletedDateIsNull(user, board);

        if (heart.isPresent()) { // 하트 취소
            if (board.getHeartCnt() > 0) {
                board.updateHeartCnt(board.getHeartCnt() - 1);
            } else {
                throw new InvalidException(ErrorCode.INVALID);
            }

            heart.get().updateDeletedDate(LocalDateTime.now());
            return false;
        } else { // 하트 추가
            board.updateHeartCnt(board.getHeartCnt() + 1);

            Heart newheart = Heart.builder()
                    .user(user)
                    .board(board)
                    .build();

            heartRepository.save(newheart);
        }

        return true;
    }

    @Transactional(readOnly = true)
    public Slice<GetTrendBoardResponse> getTrendBoardList(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Pageable pageable, String tag) {

        return boardRepository.getTrendBoardListOrderByHeartCnt(trendStartDateTime,
                        trendEndDateTime, pageable, tag)
                .map(GetTrendBoardResponse::of);
    }

    @Transactional
    public void updateViewCnt(UUID boardUUID) {
        Board board = boardRepository.findByUuidAndDeletedDateIsNull(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.addViewCnt();
    }

    @Transactional(readOnly = true)
    public List<GetPopularUsersResponse> getPopularUsers(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Long limit) {

        return boardRepository.getPopularUsersByHeartTotalCnt(trendStartDateTime, trendEndDateTime,
                        limit)
                .stream()
                .map(user -> GetPopularUsersResponse.of(user.getProfileUrl(), user.getNickname()))
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Slice<GetTrendBoardResponse> getMyTrendBoardList(LocalDateTime trendStartDateTime,
            LocalDateTime trendEndDateTime, Pageable pageable, String email) {

        User user = userService.validateUser(email);

        return boardRepository.getMyTrendBoardsListOrderByHeartCnt(trendStartDateTime,
                        trendEndDateTime, user, pageable)
                .map(GetTrendBoardResponse::of);
    }
}
