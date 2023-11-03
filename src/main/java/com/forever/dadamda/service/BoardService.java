package com.forever.dadamda.service;

import static com.forever.dadamda.service.UUIDService.generateUUID;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardContentsResponse;
import com.forever.dadamda.dto.board.GetBoardDetailResponse;
import com.forever.dadamda.dto.board.GetBoardResponse;
import com.forever.dadamda.dto.board.GetSharedBoardContentsResponse;
import com.forever.dadamda.dto.board.GetSharedBoardTitleResponse;
import com.forever.dadamda.dto.board.UpdateBoardContentsRequest;
import com.forever.dadamda.dto.board.UpdateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.service.user.UserService;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final UserService userService;
    private final BoardRepository boardRepository;
    private final AmazonS3 s3Client;

    @Transactional
    public void createBoards(String email, CreateBoardRequest createBoardRequest) {
        User user = userService.validateUser(email);

        Board board = createBoardRequest.toEntity(user, generateUUID());

        boardRepository.save(board);
    }

    @Transactional
    public void deleteBoards(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateDeletedDate(LocalDateTime.now());
    }

    @Transactional
    public void fixBoards(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
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
    public void updateBoards(String email, UUID boardUUID, UpdateBoardRequest updateBoardRequest, MultipartFile file) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateBoard(updateBoardRequest);

        if(updateBoardRequest.getIsDeleted()) {
            try {
                s3Client.deleteObject(bucketName, "thumbnail/" + board.getUuid());
            } catch(AmazonServiceException e) {
                throw new IllegalArgumentException("파일 삭제에 실패했습니다.");
            }
            board.deleteThumbnailUrl();
        } else if(file != null) {
            uploadThumbnailImage(board, file);
        }
    }

    @Transactional
    public void uploadThumbnailImage(Board board, MultipartFile file) {
         File fileObj = convertMultiPartFileToFile(file);
         String fileName = "thumbnail/" + board.getUuid();
         s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
         fileObj.delete();

         String url = s3Client.getUrl(bucketName, fileName).toString();
         board.updateThumbnailUrl(url);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 변환에 실패했습니다.");
        }
        return convertedFile;
    }

    @Transactional(readOnly = true)
    public Long getBoardCount(String email) {
        User user = userService.validateUser(email);
        return boardRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional(readOnly = true)
    public GetBoardDetailResponse getBoard(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        return boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
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

    @Transactional(readOnly = true)
    public Boolean getBoardIsShared(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        return boardRepository.findIsSharedByBoardUUID(user, boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));
    }

    @Transactional
    public void updateBoardIsShared(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board board = boardRepository.findByUserAndUuidAndDeletedDateIsNull(user, boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        board.updateIsShared(!board.isShared());
    }

    @Transactional(readOnly = true)
    public GetSharedBoardContentsResponse getSharedBoardContents(UUID boardUUID) {
        return boardRepository.findByUuidAndDeletedDateIsNullAndIsSharedIsTrue(boardUUID)
                .map(GetSharedBoardContentsResponse::of)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));
    }

    @Transactional(readOnly = true)
    public GetSharedBoardTitleResponse getSharedBoardTitle(UUID boardUUID) {
        return boardRepository.findByUuidAndDeletedDateIsNullAndIsSharedIsTrue(boardUUID)
                .map(GetSharedBoardTitleResponse::of)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));
    }


    @Transactional
    public UUID copyBoards(String email, UUID boardUUID) {
        User user = userService.validateUser(email);

        Board sharedBoard = boardRepository.findByUuidAndDeletedDateIsNullAndIsSharedIsTrue(boardUUID)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD));

        Board newBoard = Board.builder()
                .user(user)
                .title(sharedBoard.getTitle())
                .tag(sharedBoard.getTag())
                .uuid(generateUUID())
                .description(sharedBoard.getDescription())
                .authorshipUser(sharedBoard.getAuthorshipUser())
                .contents(sharedBoard.getContents())
                .thumbnailUrl(sharedBoard.getThumbnailUrl())
                .build();

        Board copyBoard = boardRepository.save(newBoard);

        return copyBoard.getUuid();
    }
}
