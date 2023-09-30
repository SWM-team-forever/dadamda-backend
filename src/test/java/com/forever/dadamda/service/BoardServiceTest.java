package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.board.BoardRepository;
import com.forever.dadamda.repository.UserRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
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
}
