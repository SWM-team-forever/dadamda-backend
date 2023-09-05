package com.forever.dadamda.repository.scrap;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Slice<Product>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);

    Optional<Product> findByIdAndUserAndDeletedDateIsNull(Long scrapId, User user);

    Long countByUserAndDeletedDateIsNull(User user);
}
