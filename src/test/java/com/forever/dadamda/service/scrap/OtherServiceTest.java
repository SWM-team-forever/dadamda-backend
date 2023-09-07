package com.forever.dadamda.service.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "/application-test.yml")
public class OtherServiceTest {

    @Autowired
    private OtherService otherService;

    String email = "1234@naver.com";

    @Test   //기타 스크랩이 없는 경우, 0개의 스크랩을 반환하는지 확인
    void should_Return_zero_other_When_Searching_for_a_keyword_in_not_existent_other() {
        //If there are no other scraps, check if it returns 0 scraps.
        //given
        //when
        //then
        int searchedOtherNumber = otherService.searchOthers(
                email, "MAC", PageRequest.of(0, 10)).getNumberOfElements();

        assertEquals( searchedOtherNumber, 0);
    }
}
