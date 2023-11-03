package com.forever.dadamda.service;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.heart.Heart;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.repository.HeartRepository;
import com.forever.dadamda.service.user.UserService;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
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
}
