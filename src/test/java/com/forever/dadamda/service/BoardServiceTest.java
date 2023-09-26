package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.dto.board.CreateBoardRequest;
import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.board.TAG;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.BoardRepository;
import com.forever.dadamda.repository.UserRepository;
import java.util.Optional;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class BoardServiceTest {

    @Autowired
    private BoardService boardService;

    @Autowired
    private BoardRepository boardRepository;

    @Autowired
    private UserRepository userRepository;

    String existentEmail = "1234@naver.com";

    @Test
    void should_the_name_description_and_heart_count_are_generated_normally_When_creating_the_board() {
        // 새로운 보드가 정상적으로 이름, 설명, 하트수가 생성되는지 확인
        //given
        CreateBoardRequest createBoardRequest = CreateBoardRequest.builder()
                .tag("ENTERTAINMENT_ART")
                .name("board test1")
                .description("board test2")
                .build();

        User user = userRepository.findByEmailAndDeletedDateIsNull(existentEmail).get();

        //when
        boardService.createBoards(existentEmail, createBoardRequest);

        //then
        Optional<Board> board = boardRepository.findFirstByUserAndDeletedDateIsNull(user);
        assertThat(board).isNotNull();
        assertThat(board.get().getName()).isEqualTo("board test1");
        assertThat(board.get().getDescription()).isEqualTo("board test2");
        assertThat(board.get().getHeartCnt()).isEqualTo(0L);
    }

    @Test
    void should_the_deleted_date_is_not_null_When_deleting_a_board() {
        // 보드를 삭제했을 때, 삭제된 날짜가 null이 아닌지 확인
        //given
        User user = userRepository.findByEmailAndDeletedDateIsNull(existentEmail).get();

        Board board = Board.builder()
                .user(user)
                .uuid(UUID.fromString("3f06af63-a93c-11e4-9797-00505690773f"))
                .isPublic(true)
                .tag(TAG.from("ENTERTAINMENT_ART"))
                .name("test")
                .build();
        boardRepository.save(board);

        Long boardId = board.getId();

        //when
        boardService.deleteBoards(existentEmail, boardId);

        //then
        assertThat(boardRepository.findById(boardId).get().getDeletedDate()).isNotNull();
    }
}
