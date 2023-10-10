package com.forever.dadamda.service;

import static com.forever.dadamda.entity.board.TAG.LIFE_SHOPPING;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.dto.board.GetBoardResponse;
import com.forever.dadamda.dto.board.UpdateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/board-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    String existentEmail = "1234@naver.com";

    @Test
    void should_the_description_and_heart_count_are_generated_normally_When_creating_the_board() {
        // 새로운 보드가 정상적으로 이름, 설명, 하트수가 생성되는지 확인
        //given
        boardRepository.deleteAll();

        CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
                .tag("ENTERTAINMENT_ART")
                .name("board normally test1")
                .description("board test2")
                .build();

        User user = userRepository.findByEmailAndDeletedDateIsNull(existentEmail).get();

        //when
        boardService.createBoards(existentEmail, createBoardRequest);

        //then
        Optional<Board> board = boardRepository.findByUserAndName(user, "board normally test1");
        assertThat(board).isNotNull();
        assertThat(board.get().getDescription()).isEqualTo("board test2");
        assertThat(board.get().getHeartCnt()).isEqualTo(0L);
    }

    @Test
    void should_the_deleted_date_is_not_null_When_deleting_a_board() {
        // 보드를 삭제했을 때, 삭제된 날짜가 null이 아닌지 확인
        //given
        //when
        boardService.deleteBoards(existentEmail, 1L);

        //then
        assertThat(boardRepository.findById(1L).get().getDeletedDate()).isNotNull();
    }

    @Test
    void should_the_fixed_date_is_before_or_equal_to_now_When_fixing_a_board() {
        // 보드를 고정했을 때, 고정된 날짜가 null이 아닌지(보드가 정상적으로 고정되는지) 확인
        //given
        //when
        boardService.fixedBoards(existentEmail, 1L);

        //then
        assertThat(boardRepository.findById(1L).get().getFixedDate()).isBeforeOrEqualTo(
                LocalDateTime.now());
    }

    @Test
    void should_the_fixed_date_is_null_When_fixing_a_fixed_board() {
        // 고정된 보드를 다시 고정할 때, 고정된 날짜가 null이 되는지(고정이 취소되는지) 확인
        //given
        //when
        boardService.fixedBoards(existentEmail, 2L);

        //then
        assertThat(boardRepository.findById(2L).get().getFixedDate()).isNull();
    }

    @Test
    void should_boards_are_sorted_by_fixed_date_and_modified_date_order_When_getting_a_list_of_boards() {
        // 보드 목록을 조회할 때, 고정된 날짜, 수정된 날짜 순으로 정렬되는지 확인
        //given
        PageRequest pageRequest = PageRequest.of(0, 10);

        //when
        Slice<GetBoardResponse> getBoardResponseSlice = boardService.getBoards(existentEmail, pageRequest);

        //then
        assertThat(getBoardResponseSlice.getContent().get(0).getBoardId()).isEqualTo(4L);
        assertThat(getBoardResponseSlice.getContent().get(1).getBoardId()).isEqualTo(2L);
        assertThat(getBoardResponseSlice.getContent().get(2).getBoardId()).isEqualTo(3L);
        assertThat(getBoardResponseSlice.getContent().get(3).getBoardId()).isEqualTo(1L);
    }

    @Test
    void should_the_tag_name_description_modified_date_are_modified_successfully_When_modifying_a_board() {
        // 보드를 수정할 때, tag, name, description, modified_date가 성공적으로 수정되는지 확인
        //given
        Long bordId = 1L;
        UpdateBoardRequest updateBoardRequest = UpdateBoardRequest.builder()
                .tag("LIFE_SHOPPING")
                .name("test")
                .description("test123")
                .build();

        //when
        boardService.updateBoards(existentEmail, bordId, updateBoardRequest);

        //then
        Board board = boardRepository.findById(bordId).get();

        assertThat(board.getModifiedDate()).isAfter(
                LocalDateTime.of(2023, 1, 2, 11, 11, 1));
        assertThat(board.getTag()).isEqualTo(LIFE_SHOPPING);
        assertThat(board.getName()).isEqualTo("test");
        assertThat(board.getDescription()).isEqualTo("test123");
    }

    @Test
    void should_it_is_not_modified_it_is_the_same_as_the_previous_one_When_modifying_a_board() {
        // 보드를 수정할 때, 이전 내용과 동일하다면, 수정되지 않는지 확인
        //given
        Long bordId = 1L;
        UpdateBoardRequest updateBoardRequest = UpdateBoardRequest.builder()
                .tag("ENTERTAINMENT_ART")
                .name("board1")
                .description("test")
                .build();

        //when
        boardService.updateBoards(existentEmail, bordId, updateBoardRequest);

        //then
        Board board = boardRepository.findById(bordId).get();

        assertThat(board.getModifiedDate()).isEqualTo(
                LocalDateTime.of(2023, 1, 1, 11, 11, 1));
    }
}
