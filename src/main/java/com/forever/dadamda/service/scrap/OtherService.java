package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.scrap.other.GetOtherResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.OtherRepository;
import com.forever.dadamda.service.user.UserService;
import lombok.RequiredArgsConstructor;
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
    public Other saveOther(WebClientBodyResponse crawlingResponse, User user, String pageUrl) {

        Other other = Other.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.getTitle())
                .thumbnailUrl(crawlingResponse.getThumbnailUrl())
                .description(crawlingResponse.getDescription())
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

    @Transactional
    public Slice<GetOtherResponse> searchOthers(String email, String keyword, Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Other> otherSlice = otherRepository
                .findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        user, keyword, keyword, pageable)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return otherSlice.map(GetOtherResponse::of);
    }
}
