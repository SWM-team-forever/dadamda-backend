package com.forever.dadamda.repository;

import com.forever.dadamda.entity.scrap.Product;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {

    Optional<Slice<Scrap>> findAllByUserAndDeletedDateIsNull(User user, Pageable pageable);
}
