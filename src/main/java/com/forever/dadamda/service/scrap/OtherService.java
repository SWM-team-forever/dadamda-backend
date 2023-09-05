package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.other.GetOtherResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.OtherRepository;
import java.util.Optional;
import com.forever.dadamda.service.user.UserService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtherService {

    private final OtherRepository otherRepository;
    private final UserService userService;

    @Transactional
    public Other saveOther(JSONObject crawlingResponse, User user, String pageUrl) {

        Other other = Other.builder().user(user).pageUrl(pageUrl)
                .title(Optional.ofNullable(crawlingResponse.get("title")).map(Object::toString)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(crawlingResponse.get("thumbnail_url"))
                        .map(Object::toString).orElse(null))
                .description(Optional.ofNullable(crawlingResponse.get("description"))
                        .map(Object::toString)
                        .orElse(null))
                .build();

        return otherRepository.save(other);
    }

    @Transactional
    public Other updateOther(User user, UpdateScrapRequest updateScrapRequest) {
        Other other = otherRepository.findByIdAndUserAndDeletedDateIsNull(
                updateScrapRequest.getScrapId(), user).orElseThrow(() -> new NotFoundException(
                ErrorCode.NOT_EXISTS_SCRAP)); //동일한 함수가 3개 이상 반복되므로 validateScrap으로 함수 따로 빼기
        other.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        return other;
    }

    @Transactional
    public Long getOtherCount(String email) {
        User user = userService.validateUser(email);
        return otherRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional
    public Slice<GetOtherResponse> getOthers(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Other> otherSlice = otherRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return otherSlice.map(GetOtherResponse::of);
    }
}
