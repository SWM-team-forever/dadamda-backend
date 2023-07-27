package com.forever.dadamda.service.user;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.UserRepository;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User validateUser(String email) {
        return userRepository.findByEmailAndDeletedDateIsNull(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_EXISTS_MEMBER)
        );
    }

    @Transactional
    public String getProfileUrl(String email) {
        return validateUser(email).getProfileUrl();
    }

    @Transactional
    public GetUserInfoResponse getUserInfo(String email) {
        User user = validateUser(email);
        return GetUserInfoResponse.of(user.getName(), user.getEmail(), user.getProvider(),
                user.getProfileUrl());
    }

    @Transactional
    public void deleteUser(String email) {
        User user = validateUser(email);
        user.updateDeletedDate(LocalDateTime.now());
    }
}
