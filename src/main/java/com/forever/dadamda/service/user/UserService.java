package com.forever.dadamda.service.user;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.user.GetUserInfoResponse;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.TimeService;
import io.sentry.Sentry;
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

    @Transactional(readOnly = true)
    public GetUserInfoResponse getUserInfo(String email) {
        User user = validateUser(email);

        return GetUserInfoResponse.builder().name(user.getName()).email(user.getEmail())
                .profileUrl(user.getProfileUrl()).provider(user.getProvider())
                .nickname(user.getNickname())
                .createdAt(TimeService.fromLocalDateTime(user.getCreatedDate())).build();
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

        String fileName = "profileImage/" + user.getUuid();

        validateExist(file);

        File convertedFile = new File("/tmp/" + file.getOriginalFilename());
        try (FileOutputStream fileOutputStream = new FileOutputStream(convertedFile)){
            fileOutputStream.write(file.getBytes());
        } catch (IOException e) {
            Sentry.captureException(e);
            throw new IllegalArgumentException("파일 저장 중 에러가 발생했습니다.");
        }

        // upload file
        PutObjectRequest request = new PutObjectRequest(bucketName, fileName, convertedFile);
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        request.setMetadata(metadata);
        s3Client.putObject(request);

        // delete file
        convertedFile.delete();

        String url = s3Client.getUrl(bucketName, fileName).toString();
        user.updateProfileImage(url);
    }
    
    private void validateExist(MultipartFile file) {
        if(file.isEmpty()) {
            throw new InvalidException(ErrorCode.NOT_EXISTS);
        }
    }

    @Transactional
    public void deleteProfileImage(String email) {
        User user = validateUser(email);
        user.deleteProfileImage();
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
