package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import com.forever.dadamda.service.WebClientService;
import com.forever.dadamda.service.user.UserService;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ScrapService {

    private final ScrapRepository scrapRepository;
    private final VideoService videoService;
    private final ArticleService articleService;
    private final ProductService productService;
    private final OtherService otherService;
    private final WebClientService webClientService;
    private final UserService userService;

    @Transactional
    public CreateScrapResponse createScraps(String email, String pageUrl) throws ParseException {
        User user = userService.validateUser(email);

        //1. 해당 링크 중복 확인 (삭제된 링크면 중복 X)
        boolean isPresentItem = scrapRepository.findByPageUrlAndUserAndDeletedDateIsNull(pageUrl,
                user).isPresent();
        if (isPresentItem) {
            throw new InvalidException(ErrorCode.INVALID_DUPLICATED_SCRAP);
        }

        saveScraps(user, pageUrl);

        return CreateScrapResponse.of(pageUrl);
    }

    @Transactional
    public Scrap saveScraps(User user, String pageUrl) throws ParseException {
        //2. 람다에게 api 요청
        JSONObject crawlingResponse = webClientService.crawlingItem(pageUrl);

        String type = "";
        try {
            type = crawlingResponse.get("type").toString();
        } catch (NullPointerException e) {
            throw new NotFoundException(ErrorCode.NOT_EXISTS);
        }

        //3. DB 저장
        switch (type) {
            case "video":
                return videoService.saveVideo(crawlingResponse, user, pageUrl);
            case "article":
                return articleService.saveArticle(crawlingResponse, user, pageUrl);
            case "product":
                return productService.saveProduct(crawlingResponse, user, pageUrl);
        }
        return otherService.saveOther(crawlingResponse, user, pageUrl);
    }

    @Transactional
    public void deleteScraps(String email, Long scrapId) {
        User user = userService.validateUser(email);

        Scrap item = scrapRepository.findByIdAndUserAndDeletedDateIsNull(scrapId, user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        item.updateDeletedDate(LocalDateTime.now());
    }

    @Transactional
    public Slice<GetScrapResponse> getScraps(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Scrap> scrapSlice = scrapRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetScrapResponse::of);
    }

    @Transactional
    public Scrap updateScraps(String email, UpdateScrapRequest updateScrapRequest) {
        User user = userService.validateUser(email);

        switch (updateScrapRequest.getDType()) {
            case "product":
                return productService.updateProduct(user, updateScrapRequest);
            case "article":
                return articleService.updateArticle(user, updateScrapRequest);
            case "video":
                return videoService.updateVideo(user, updateScrapRequest);
            default:
                return otherService.updateOther(user, updateScrapRequest);
        }
    }

    @Transactional
    public Long getScrapCount(String email) {
        User user = userService.validateUser(email);
        return scrapRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional
    public Slice<GetScrapResponse> searchScraps(String email, String keyword, Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Scrap> scrapSlice = scrapRepository
                .findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        user, keyword, keyword, pageable)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetScrapResponse::of);
    }
}
