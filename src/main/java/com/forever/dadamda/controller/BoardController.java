package com.forever.dadamda.controller;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardContentsResponse;
import com.forever.dadamda.dto.board.GetBoardCountResponse;
import com.forever.dadamda.dto.board.GetBoardIsSharedResponse;
import com.forever.dadamda.dto.board.GetBoardResponse;
import com.forever.dadamda.dto.board.UpdateBoardContentsRequest;
import com.forever.dadamda.dto.board.UpdateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardDetailResponse;
import com.forever.dadamda.service.BoardService;
import io.swagger.v3.oas.annotations.Operation;
import java.util.UUID;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RequiredArgsConstructor
@RestController
public class BoardController {

    private final BoardService boardService;

    @Operation(summary = "보드 생성", description = "1개의 보드를 생성합니다.")
    @PostMapping("/v1/boards")
    public ApiResponse<String> createBoards(
            @Valid @RequestBody CreateBoardRequest createBoardRequest,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.createBoards(email, createBoardRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "보드 삭제", description = "1개의 보드를 삭제합니다.")
    @DeleteMapping("/v1/boards/{boardUUID}")
    public ApiResponse<String> deleteBoards(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.deleteBoards(email, UUID.fromString(boardUUID));
        return ApiResponse.success();
    }

    @Operation(summary = "보드 고정", description = "1개의 보드를 보드 카테고리에서 상단에 고정합니다.")
    @PatchMapping("/v1/boards/{boardUUID}/fix")
    public ApiResponse<String> fixBoards(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.fixBoards(email, UUID.fromString(boardUUID));
        return ApiResponse.success();
    }

    @Operation(summary = "보드 목록 조회", description = "여러 개의 보드를 조회합니다")
    @GetMapping("/v1/boards")
    public ApiResponse<Slice<GetBoardResponse>> getBoardList(Pageable pageable,
            Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(boardService.getBoardList(email, pageable));
    }

    @Operation(summary = "전체 보드 개수 조회", description = "전체 보드 개수 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/boards/count")
    public ApiResponse<GetBoardCountResponse> getBoardCount(Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(GetBoardCountResponse.of(boardService.getBoardCount(email)));
    }

    @Operation(summary = "보드 내용 조회", description = "보드 정보를 조회할 수 있습니다.")
    @GetMapping("/v1/boards/{boardUUID}")
    public ApiResponse<GetBoardDetailResponse> getBoards(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID, Authentication authentication) {
        String email = authentication.getName();
        return ApiResponse.success(boardService.getBoard(email, UUID.fromString(boardUUID)));
    }

    @Operation(summary = "보드 내용 수정", description = "1개의 보드의 이름, 설명, 태그를 수정합니다.")
    @PatchMapping("/v1/boards/{boardUUID}")
    public ApiResponse<String> updateBoards(
            @PathVariable @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            @Valid @RequestBody UpdateBoardRequest updateBoardRequest,
            Authentication authentication) {
        String email = authentication.getName();
        boardService.updateBoards(email, UUID.fromString(boardUUID), updateBoardRequest);
        return ApiResponse.success();
    }

    @Operation(summary = "보드 검색", description = "보드를 보드명으로 검색할 수 있습니다.")
    @GetMapping("/v1/boards/search")
    public ApiResponse<Slice<GetBoardResponse>> searchBoards(
            @RequestParam("keyword") @NotBlank String keyword,
            Pageable pageable,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(boardService.searchBoards(email, keyword, pageable));
    }

    @Operation(summary = "보드 컨텐츠 수정", description = "보드의 컨텐츠를 수정합니다.")
    @PatchMapping("/v1/boards/{boardUUID}/contents")
    public ApiResponse<String> updateBoardContents(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$", message = "UUID가 올바르지 않습니다.") String boardUUID,
            @Valid @RequestBody UpdateBoardContentsRequest updateBoardContentsRequest,
            Authentication authentication) {

        String email = authentication.getName();
        boardService.updateBoardContents(email, UUID.fromString(boardUUID), updateBoardContentsRequest);

        return ApiResponse.success();
    }

    @Operation(summary = "보드 컨텐츠 조회", description = "보드의 컨텐츠를 조회합니다.")
    @GetMapping("/v1/boards/{boardUUID}/contents")
    public ApiResponse<GetBoardContentsResponse> getBoardContents(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(boardService.getBoardContents(email, UUID.fromString(boardUUID)));
    }

    @Operation(summary = "보드 공유 여부 조회", description = "보드의 공유 여부를 조회합니다.")
    @GetMapping("/v1/boards/isShared/{boardUUID}")
    public ApiResponse<GetBoardIsSharedResponse> getBoardIsShared(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {

        String email = authentication.getName();

        return ApiResponse.success(GetBoardIsSharedResponse.of(
                boardService.getBoardIsShared(email, UUID.fromString(boardUUID))));
    }

    @Operation(summary = "보드 공유 여부 변경", description = "보드의 공유 여부를 변경합니다.")
    @PatchMapping("/v1/boards/isShared/{boardUUID}")
    public ApiResponse<String> updateBoardIsShared(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID,
            Authentication authentication) {

        String email = authentication.getName();
        boardService.updateBoardIsShared(email, UUID.fromString(boardUUID));

        return ApiResponse.success();
    }

    @Operation(summary = "공유된 보드 컨텐츠 조회", description = "공유된 보드 컨텐츠를 조회합니다.")
    @GetMapping("/ov1/share/boards/{boardUUID}")
    public ApiResponse<GetBoardContentsResponse> getSharedBoardContents(
            @PathVariable @NotNull @Pattern(regexp = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$",
                    message = "UUID가 올바르지 않습니다.") String boardUUID) {

        return ApiResponse.success(boardService.getSharedBoardContents(UUID.fromString(boardUUID)));
    }
}
