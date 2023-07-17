package com.forever.dadamda.service.item;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.CreateScrapResponse;
import com.forever.dadamda.entity.item.Item;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.ItemRepository;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.service.WebClientService;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final UserRepository userRepository;
    private final ItemRepository itemRepository;
    private final VideoService videoService;
    private final ArticleService articleService;
    private final ProductService productService;
    private final WebClientService webClientService;

    @Transactional
    public CreateScrapResponse createScraps(String email, String pageUrl) throws ParseException {
        //1. 해당 링크 중복 확인 (삭제된 링크면 중복 X)
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new NotFoundException(ErrorCode.NOT_EXISTS_MEMBER)
        );
        boolean isPresentItem = itemRepository
                .findByPageUrlAndUserAndDeletedDateIsNull(pageUrl, user).isPresent();
        if (isPresentItem) {
            throw new InvalidException(ErrorCode.INVALID_DUPLICATED_SCRAP);
        }

        //2. 람다에게 api 요청
        JSONObject crawlingResponse = webClientService.crawlingItem(pageUrl);
        System.out.println(crawlingResponse);
        String type = crawlingResponse.get("type").toString();

        //3. DB 저장
        switch (type) {
            case "video":
                videoService.saveVideo(crawlingResponse, user, pageUrl);
                break;
            case "article":
                articleService.saveArticle(crawlingResponse, user, pageUrl);
                break;
            case "product":
                productService.saveProduct(crawlingResponse, user, pageUrl);
                break;
            case "other":
                System.out.println(crawlingResponse);
                saveOther(crawlingResponse, user, pageUrl);
                break;
        }

        return CreateScrapResponse.of(pageUrl);
    }

    @Transactional
    public void saveOther(JSONObject crawlingResponse, User user, String pageUrl) {
        Item other = new Item(user, pageUrl, crawlingResponse.get("title").toString(),
                crawlingResponse.get("thumbnail_url").toString(),
                crawlingResponse.get("description").toString());

        itemRepository.save(other);
    }
}
