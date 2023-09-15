package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.memo.DeleteMemoRequest;
import com.forever.dadamda.dto.memo.UpdateMemoRequest;
import com.forever.dadamda.entity.Memo;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.MemoRepository;
import java.time.LocalDateTime;
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

    @Test
    void should_update_date_and_memo_text_are_updated_becomes_not_null_When_modifying_a_note() {
        // 스크랩의 메모를 수정할 때, update_date와 memo_text가 업데이트된다.
        UpdateMemoRequest updateMemoRequest = UpdateMemoRequest.builder().memoText("안녕하세요!")
                .scrapId(1L).memoId(1L).build();

        //when
        memoService.updateMemo(existentEmail, updateMemoRequest);

        //then
        LocalDateTime memoPreviousUpdateDate = LocalDateTime.of(2023, 1, 1, 11, 11, 1);

        Memo memo = (memoRepository.findById(1L)).get();
        assertThat(memo.getModifiedDate()).isAfter(memoPreviousUpdateDate);
        assertThat(memo.getMemoText()).isEqualTo("안녕하세요!");
    }
}
