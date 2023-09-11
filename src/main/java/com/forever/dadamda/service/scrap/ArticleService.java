package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.article.GetArticleResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.ArticleRepository;
import com.forever.dadamda.service.TimeService;
import com.forever.dadamda.service.user.UserService;
import java.util.Optional;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public Article saveArticle(JSONObject crawlingResponse, User user, String pageUrl) {

        Article article = Article.builder().user(user).pageUrl(pageUrl)
                .title(Optional.ofNullable(crawlingResponse.get("title")).map(Object::toString)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(crawlingResponse.get("thumbnail_url"))
                        .map(Object::toString).orElse(null))
                .description(Optional.ofNullable(crawlingResponse.get("description"))
                        .map(Object::toString).orElse(null))
                .author(Optional.ofNullable(crawlingResponse.get("author")).map(Object::toString)
                        .orElse(null))
                .authorImageUrl(Optional.ofNullable(crawlingResponse.get("author_image_url"))
                        .map(Object::toString).orElse(null))
                .blogName(
                        Optional.ofNullable(crawlingResponse.get("blog_name")).map(Object::toString)
                                .orElse(null))
                .publishedDate(Optional.ofNullable(crawlingResponse.get("published_date"))
                        .map(Object::toString).map(Long::parseLong).map(
                                TimeService::fromUnixTime).orElse(null))
                .siteName(
                        Optional.ofNullable(crawlingResponse.get("site_name")).map(Object::toString)
                                .orElse(null)).build();

        return articleRepository.save(article);
    }

    @Transactional
    public Article updateArticle(User user, UpdateScrapRequest updateScrapRequest) {
        Article article = articleRepository.findByIdAndUserAndDeletedDateIsNull(
                        updateScrapRequest.getScrapId(), user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
        article.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        article.updateArticle(updateScrapRequest.getAuthor(),
                updateScrapRequest.getBlogName());

        return article;
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
    public Slice<GetArticleResponse> searchArticles(String email, String keyword, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Article> scrapSlice = articleRepository
                .findAllByUserAndDeletedDateIsNullAndTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCase(
                        user, keyword, keyword, pageRequest)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return scrapSlice.map(GetArticleResponse::of);
    }
}
