package com.forever.dadamda.repository.scrap.product;

import static com.forever.dadamda.entity.scrap.QProduct.product;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Slice<Product> searchKeywordInProductOrderByCreatedDateDesc(User user, String keyword,
            Pageable pageable) {
        List<Product> contents = queryFactory
                .selectFrom(product)
                .where(
                        product.user.eq(user)
                                .and(product.deletedDate.isNull())
                                .and(product.title.containsIgnoreCase(keyword)
                                        .or(product.description.containsIgnoreCase(keyword)))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize() + 1)
                .orderBy(product.createdDate.desc())
                .fetch();

        return new SliceImpl<>(contents, pageable, hasNextPage(contents, pageable.getPageSize()));
    }

    private boolean hasNextPage(List<Product> contents, int pageSize) {
        if (contents.size() > pageSize) {
            contents.remove(pageSize);
            return true;
        }
        return false;
    }
}
