package com.forever.dadamda.service.scrap;

import com.forever.dadamda.entity.scrap.Article;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.ArticleRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepository articleRepository;

    @Transactional
    public void saveArticle(JSONObject crawlingResponse, User user, String pageUrl) {
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

        articleRepository.save(article);
    }
}
