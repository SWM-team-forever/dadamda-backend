package com.forever.dadamda.repository.scrap;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.TestConfig;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.product.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
@Import(TestConfig.class)
public class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    String email = "1234@naver.com";

    @Test
    void should_memoList_from_the_scrap_are_also_returned_when_searching_for_keywords() {
        // 키워드 검색할 때, 해당 스크랩의 메모도 같이 반환된다.
        // given
        User user = userRepository.findByEmailAndDeletedDateIsNull(email).get();
        String keyword = "coupang";
        Pageable pageable = PageRequest.of(0, 2);

        //when
        Slice<Product> results = productRepository.searchKeywordInProductOrderByCreatedDateDesc(user,
                keyword, pageable);

        // then
        assertThat(results.getContent().get(0).getMemoList().get(0).getMemoText()).isEqualTo("Hello 1");
    }
}
