package com.forever.dadamda.service;

import static com.forever.dadamda.service.UUIDService.generateUUID;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardContentsResponse;
import com.forever.dadamda.dto.board.GetBoardDetailResponse;
import com.forever.dadamda.dto.board.GetBoardResponse;
import com.forever.dadamda.dto.board.UpdateBoardContentsRequest;
import com.forever.dadamda.dto.board.UpdateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.service.user.UserService;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
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

        if (board.getFixedDate() == null) {
            board.updateFixedDate(LocalDateTime.now());
        } else {
            board.updateFixedDate(null);
        }
    }

    @Transactional(readOnly = true)
    public Slice<GetBoardResponse> getBoardList(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Board> boardSlice = boardRepository.getBoardsList(user, pageable);

        return boardSlice.map(GetBoardResponse::of);
    }

    @Transactional
    public void updateBoards(String email, Long boardId, UpdateBoardRequest updateBoardRequest) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndIdAndDeletedDateIsNull(user, boardId)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateBoard(updateBoardRequest);
    }

    @Transactional(readOnly = true)
    public Long getBoardCount(String email) {
        User user = userService.validateUser(email);
        return boardRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional(readOnly = true)
    public GetBoardDetailResponse getBoard(String email, Long boardId) {
        User user = userService.validateUser(email);

        return boardRepository.findByUserAndIdAndDeletedDateIsNull(user, boardId)
                .map(GetBoardDetailResponse::of)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));
    }

    @Transactional(readOnly = true)
    public Slice<GetBoardResponse> searchBoards(String email, String keyword, Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Board> boardSlice = boardRepository.searchKeywordInBoardList(user, keyword, pageable);

        return boardSlice.map(GetBoardResponse::of);
    }

    @Transactional
    public void updateBoardContents(String email, UUID boardUUID,
            UpdateBoardContentsRequest updateBoardContentsRequest) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateContents(updateBoardContentsRequest);
    }

    @Transactional(readOnly = true)
    public GetBoardContentsResponse getBoardContents(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        return boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
                .map(GetBoardContentsResponse::of)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));
    }
}
