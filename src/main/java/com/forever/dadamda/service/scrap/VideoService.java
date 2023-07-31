package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class VideoService {

    private final VideoRepository videoRepository;

    @Transactional
    public Video saveVideo(JSONObject crawlingResponse, User user, String pageUrl) {
        Long watchedCnt = null;
        Long playTime = null;

        if (crawlingResponse.get("watched_cnt") != null) {
            watchedCnt = Long.parseLong(crawlingResponse.get("watched_cnt").toString());
        }
        if (crawlingResponse.get("play_time") != null) {
            playTime = Long.parseLong(crawlingResponse.get("play_time").toString());
        }

        Video video = Video.builder().user(user).pageUrl(pageUrl)
                .title(crawlingResponse.get("title").toString())
                .thumbnailUrl(crawlingResponse.get("thumbnail_url").toString())
                .description(crawlingResponse.get("description").toString())
                .embedUrl(crawlingResponse.get("embed_url").toString())
                .channelName(crawlingResponse.get("channel_name").toString())
                //.channelImageUrl(crawlingVideoResponse.get("channel_image_url").toString())
                .watchedCnt(watchedCnt).playTime(playTime)
                //.publishedDate(LocalDateTime.parse(crawlingVideoResponse.get("published_date").toString(), formatter))
                .siteName(crawlingResponse.get("site_name").toString())
                .genre(crawlingResponse.get("genre").toString()).build();

        return videoRepository.save(video);
    }

    @Transactional
    public Video updateVideo(User user, UpdateScrapRequest updateScrapRequest) {
        Video video = videoRepository.findByIdAndUserAndDeletedDateIsNull(
                        updateScrapRequest.getScrapId(), user)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));
        video.update(updateScrapRequest.getTitle(), updateScrapRequest.getDescription(),
                updateScrapRequest.getSiteName());
        video.updateVideo(updateScrapRequest.getChannelName(),
                updateScrapRequest.getChannelImageUrl(), updateScrapRequest.getWatchedCnt(),
                updateScrapRequest.getPlayTime(), updateScrapRequest.getPublishedDate(),
                updateScrapRequest.getGenre());
        return video;
    }
}
