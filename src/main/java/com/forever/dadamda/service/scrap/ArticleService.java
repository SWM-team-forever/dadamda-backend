package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.scrap.article.GetArticleResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ArticleRepository;
import com.forever.dadamda.service.TimeService;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public Article saveArticle(WebClientBodyResponse crawlingResponse, User user, String pageUrl) {

        Article article = Article.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.getTitle())
                .thumbnailUrl(crawlingResponse.getThumbnailUrl())
                .description(crawlingResponse.getDescription())
                .author(crawlingResponse.getAuthor())
                .authorImageUrl(crawlingResponse.getAuthorImageUrl())
                .blogName(crawlingResponse.getBlogName())
                .publishedDate(TimeService.fromUnixTime(crawlingResponse.getPublishedDate()))
                .siteName(crawlingResponse.getSiteName()).build();

        return articleRepository.save(article);
    }

    @Transactional
    public void updateArticle(User user, UpdateScrapRequest updateScrapRequest) {
        Article article = articleRepository.findByIdAndUserAndDeletedDateIsNull(
                        updateScrapRequest.getScrapId(), user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
        article.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        article.updateArticle(updateScrapRequest.getAuthor(),
                updateScrapRequest.getBlogName());

    }

    @Transactional
    public Long getArticleCount(String email) {
        User user = userService.validateUser(email);
        return articleRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional
    public Slice<GetArticleResponse> getArticles(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Article> articleSlice = articleRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return articleSlice.map(GetArticleResponse::of);
    }

    @Transactional
    public Slice<GetArticleResponse> searchArticles(String email, String keyword,
            Pageable pageable) {
        User user = userService.validateUser(email);

        Slice<Article> scrapSlice = articleRepository
                .findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        user, keyword, keyword, pageable)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetArticleResponse::of);
    }
}
