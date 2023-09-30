package com.forever.dadamda.service;

import static com.forever.dadamda.service.UUIDService.generateUUID;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.service.user.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BoardService {
    private final UserService userService;
    private final BoardRepository boardRepository;

    @Transactional
    public void createBoards(String email, CreateBoardRequest createBoardRequest) {
        User user = userService.validateUser(email);

        Board board = createBoardRequest.toEntity(user, generateUUID(), true);

        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoards(String email, Long boardId) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndIdAndDeletedDateIsNull(user, boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateDeletedDate(LocalDateTime.now());
    }

    @Transactional
    public void fixedBoards(String email, Long boardId) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndIdAndDeletedDateIsNull(user, boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        if(board.getFixedDate() == null) {
            board.updateFixedDate(LocalDateTime.now());
        } else {
            board.updateFixedDate(null);
        }
    }
}
