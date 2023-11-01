package com.forever.dadamda.service.user;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.UserRepository;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${application.bucket.name}")
    private String bucketName;

    private final UserRepository userRepository;
    private final AmazonS3 s3Client;

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
        return GetUserInfoResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .profileUrl(user.getProfileUrl())
                .provider(user.getProvider())
                .build();
    }

    @Transactional
    public void deleteUser(String email) {
        User user = validateUser(email);
        user.updateDeletedDate(LocalDateTime.now());
    }

    @Transactional
    public void uploadProfileImage(String email, MultipartFile file) {
        User user = userRepository.findByEmailAndDeletedDateIsNull(email)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_MEMBER));

        File fileObj = convertMultiPartFileToFile(file);
        String fileName = "profileImage/" + user.getUuid();
        s3Client.putObject(new PutObjectRequest(bucketName, fileName, fileObj));
        fileObj.delete();

        String url = s3Client.getUrl(bucketName, fileName).toString();
        user.updateProfileImage(url);
    }

    private File convertMultiPartFileToFile(MultipartFile file) {
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)) {
            fos.write(file.getBytes());
        } catch (IOException e) {
            throw new IllegalArgumentException("파일 변환에 실패했습니다.");
        }
        return convertedFile;
    }

    @Transactional
    public void updateNickname(String nickname, String email) {
        User user = validateUser(email);

        if(userRepository.existsByNickname(nickname)) {
            throw new InvalidException(ErrorCode.INVALID_DUPLICATED_NICKNAME);
        }

        user.updateNickname(nickname);
    }
}
