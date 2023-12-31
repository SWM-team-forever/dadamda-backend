package com.forever.dadamda.repository;

import com.forever.dadamda.entity.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmailAndDeletedDateIsNull(String email);

    Boolean existsByNickname(String nickname);
}
