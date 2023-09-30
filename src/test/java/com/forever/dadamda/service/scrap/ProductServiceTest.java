package com.forever.dadamda.service.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.forever.dadamda.dto.scrap.product.GetProductResponse;
import com.forever.dadamda.service.TimeService;
import java.time.LocalDateTime;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "classpath:truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "classpath:setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@TestPropertySource(locations = "classpath:application-test.yml")
public class ProductServiceTest {

    @Autowired
    private ProductService productService;

    String email = "1234@naver.com";

    @Test //한 개의 상품에 해당하는 키워드를 검색했을 때 1개의 상품을 반환하는지 확인
    void should_Return_one_product_When_Searching_for_a_keyword_that_corresponds_to_one_product() {
        //given
        //when
        int searchedProductNumber = productService.searchProducts(email, "MAC", PageRequest.of(0, 10)).getNumberOfElements();

        //then
        assertEquals( searchedProductNumber, 1);
    }

    @Test // 상품에 메모를 작성했을 때, 메모의 생성된 날짜가 반환되는지 확인
    void should_Return_created_date_of_notes_When_create_notes_on_product() {
        // When you create a note on a product, make sure that the date the note was created is returned

        //given
        //when
        Slice<GetProductResponse> getProductResponse = productService.getProducts(email, PageRequest.of(0, 10));
        Long memoCreatedDate = getProductResponse.getContent().get(0).getMemoList().get(0).getCreatedDate();

        //then
        assertEquals( memoCreatedDate, TimeService.fromLocalDateTime(LocalDateTime.of(2023, 1, 1, 11, 11, 1)));
    }
}
