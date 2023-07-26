package com.forever.dadamda.service.user;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    @Transactional
    public User validateUser(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_EXISTS_MEMBER)
        );
    }

    @Transactional
    public String getProfileUrl(String email){
        return validateUser(email).getProfileUrl();
    }
}
