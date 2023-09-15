package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.memo.DeleteMemoRequest;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.MemoRepository;
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
public class MemoServiceTest {

    @Autowired
    private MemoService memoService;

    @Autowired
    private MemoRepository memoRepository;

    String existentEmail = "1234@naver.com";
    String nonExistentEmail = "123@naver.com";

    @Test
    void should_NotFoundException_occurs_When_deleting_a_note_from_a_scrap_saved_by_another_person() {
        // 다른 유저가 저장한 스크랩의 메모를 삭제할 때, NotFoundException(NOT_EXISTS_SCRAP) 예외가 발생한다.
        DeleteMemoRequest deleteMemoRequest = DeleteMemoRequest.builder().memoId(1L).scrapId(1L)
                .build();

        //when
        //then
        assertThatThrownBy(
                () -> memoService.deleteMemo(nonExistentEmail, deleteMemoRequest)).hasSameClassAs(
                new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
    }

    @Test
    void should_the_deleted_date_of_the_note_becomes_not_null_When_deleting_a_note() {
        // 스크랩의 메모를 삭제할 때, deleted_date가 업데이트된다.
        DeleteMemoRequest deleteMemoRequest = DeleteMemoRequest.builder().memoId(1L).scrapId(1L)
                .build();

        //when
        memoService.deleteMemo(existentEmail, deleteMemoRequest);

        //then
        assertThat((memoRepository.findById(1L)).get().getDeletedDate()).isNotNull();
    }
}
