package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Other;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.OtherRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OtherService {

    private final OtherRepository otherRepository;

    @Transactional
    public Other saveOther(JSONObject crawlingResponse, User user, String pageUrl) {
        Other other = Other.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.get("title").toString())
                .thumbnailUrl(crawlingResponse.get("thumbnail_url").toString())
                .description(crawlingResponse.get("description").toString()).build();

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
}
