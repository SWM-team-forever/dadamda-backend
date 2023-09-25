package com.forever.dadamda.repository.scrap.product;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ProductRepositoryCustom {

    Slice<Product> searchKeywordInProductOrderByCreatedDateDesc(
            User user, String keyword, Pageable pageable);

}
