package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.board.Board;
import com.forever.dadamda.entity.heart.Heart;
import com.forever.dadamda.repository.HeartRepository;
import com.forever.dadamda.repository.board.BoardRepository;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/board-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class TrendServiceTest {

    @Autowired
    private TrendService trendService;

    @Autowired
    private HeartRepository heartRepository;

    @Autowired
    private BoardRepository boardRepository;

    String existentEmail = "1234@naver.com";
    UUID board1UUID = UUID.fromString("30373832-6566-3438-2d61-3433392d3131");

    @Test
    @Transactional
    void should_the_number_of_hearts_on_the_board_increases_and_heart_is_added_When_adding_hearts_on_the_trending_board() {
        // 트랜딩 보드의 하트를 추가할 때, 보드의 하트개수가 증가하고, 하트가 추가되는지 확인한다.
        //given
        //when
        trendService.addHearts(existentEmail, board1UUID);

        //then
        Board board = boardRepository.findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(board1UUID).get();
        List<Heart> heartList = heartRepository.findAll();
        Heart heart = heartList.get(0);

        assertThat(board.getHeartCnt()).isEqualTo(11);
        assertThat(heartList.size()).isEqualTo(1);
        assertThat(heart.getDeletedDate()).isNull();
        assertThat(heart.getUser().getEmail()).isEqualTo(existentEmail);
        assertThat(heart.getBoard().getUuid()).isEqualTo(board1UUID);
    }

    @Test
    @Transactional
    void should_the_number_of_hearts_on_the_board_decreases_and_heart_is_deleted_When_heart_is_canceled_on_a_board() {
        // 트랜딩 보드의 하트를 취소할 때, 보드의 하트 개수가 감소하고, 하트가 삭제되는지 확인한다.
        //given
        trendService.addHearts(existentEmail, board1UUID);

        //when
        trendService.deleteHearts(existentEmail, board1UUID);

        //then
        Board board = boardRepository.findByUuidAndDeletedDateIsNullAndIsPublicIsTrue(board1UUID).get();
        List<Heart> heartList = heartRepository.findAll();
        Heart heart = heartList.get(0);

        assertThat(board.getHeartCnt()).isEqualTo(10);
        assertThat(heartList.size()).isEqualTo(1);
        assertThat(heart.getDeletedDate()).isNotNull();
        assertThat(heart.getUser().getEmail()).isEqualTo(existentEmail);
        assertThat(heart.getBoard().getUuid()).isEqualTo(board1UUID);
    }

    void should_the_number_of_viewCnt_on_the_board_increases_When_viewing_the_board() {
        // 트랜딩 보드를 조회할 때, 보드의 조회수가 증가하는지 확인한다.
        //given
        //when
        trendService.updateViewCnt(board1UUID);

        //then
        Board board = boardRepository.findByUuidAndDeletedDateIsNull(board1UUID).get();

        assertThat(board.getViewCnt()).isEqualTo(1);
    }
}
