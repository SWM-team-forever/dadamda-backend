package com.forever.dadamda.repository;

import com.forever.dadamda.entity.item.Item;
import com.forever.dadamda.entity.user.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByPageUrlAndUserAndDeletedDateIsNull(String pageUrl, User user);
}
