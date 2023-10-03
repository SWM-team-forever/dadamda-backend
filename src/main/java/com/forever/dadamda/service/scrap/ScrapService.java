package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.dto.scrap.GetScrapResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Scrap;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.MemoRepository;
import com.forever.dadamda.repository.scrap.ScrapRepository;
import com.forever.dadamda.service.WebClientService;
import com.forever.dadamda.service.user.UserService;
import java.time.LocalDateTime;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import net.minidev.json.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;
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
    private final PlaceService placeService;
    private final MemoRepository memoRepository;

    @Value("${crawling.server.post.api.endPoint}")
    private String crawlingApiEndPoint;

    @Transactional
    public CreateScrapResponse createScraps(String email, String pageUrl) throws ParseException {
        User user = userService.validateUser(email);

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
        WebClientBodyResponse crawlingResponse = webClientService.crawlingItem(crawlingApiEndPoint, pageUrl);

        return Optional.ofNullable(crawlingResponse)
                .map(response -> {
                    String type = response.getType();
                    if(response.getDescription() != null) {
                        if(response.getDescription().length() > 1000) {
                            response.setDescription(response.getDescription().substring(0, 1000));
                        }
                    }
                    switch (type) {
                        case "video":
                            return videoService.saveVideo(response, user, pageUrl);
                        case "article":
                            return articleService.saveArticle(response, user, pageUrl);
                        case "product":
                            return productService.saveProduct(response, user, pageUrl);
                        case "place":
                            return placeService.savePlace(response, user, pageUrl);
                        default:
                            return otherService.saveOther(response, user, pageUrl);
                    }
                })
                .orElseGet(() -> otherService.saveOther(new WebClientBodyResponse(), user, pageUrl));
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

        return scrapSlice.map(scrap -> GetScrapResponse.of(scrap,
                memoRepository.findMemosByScrapAndDeletedDateIsNull(scrap))
        );
    }

    @Transactional
    public void updateScraps(String email, UpdateScrapRequest updateScrapRequest) {
        User user = userService.validateUser(email);

        switch (updateScrapRequest.getDType()) {
            case "product":
                productService.updateProduct(user, updateScrapRequest);
                return;
            case "article":
                articleService.updateArticle(user, updateScrapRequest);
                return;
            case "video":
                videoService.updateVideo(user, updateScrapRequest);
                return;
            default:
                otherService.updateOther(user, updateScrapRequest);
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

        Slice<Scrap> scrapSlice = scrapRepository.searchKeywordInScrapOrderByCreatedDateDesc(user,
                keyword, pageable);

        return scrapSlice.map(scrap -> GetScrapResponse.of(scrap,
                memoRepository.findMemosByScrapAndDeletedDateIsNull(scrap)));
    }
}
