package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.GetArticleResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.ArticleRepository;
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
public class ArticleService {

    private final ArticleRepository articleRepository;
    private final UserService userService;

    @Transactional
    public Article saveArticle(JSONObject crawlingResponse, User user, String pageUrl) {
        String authorImageUrl = null;

        if (crawlingResponse.get("author_image_url") != null) {
            authorImageUrl = crawlingResponse.get("author_image_url").toString();
        }

        Article article = Article.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.get("title").toString())
                .thumbnailUrl(crawlingResponse.get("thumbnail_url").toString())
                .description(crawlingResponse.get("description").toString())
                .author(crawlingResponse.get("author").toString()).authorImageUrl(authorImageUrl)
                .blogName(crawlingResponse.get("blog_name").toString())
                //.publishedDate(LocalDateTime.parse(crawlingVideoResponse.get("published_date").toString(), formatter))
                .siteName(crawlingResponse.get("site_name").toString()).build();

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
                updateScrapRequest.getAuthorImageUrl(), updateScrapRequest.getPublishedDate(),
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
}
