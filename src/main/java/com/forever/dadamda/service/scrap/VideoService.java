package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.GetVideoResponse;
import com.forever.dadamda.dto.scrap.UpdateScrapRequest;
import com.forever.dadamda.entity.scrap.Video;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.VideoRepository;
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
public class VideoService {

    private final VideoRepository videoRepository;
    private final UserService userService;

    public static String formatViewCount(long count) {
        if (count >= 100000000) {
            double billionCount = count / 100000000.0;
            return formatCountWithUnit(billionCount, "억회");
        } else if (count >= 10000) {
            double millionCount = count / 10000.0;
            return formatCountWithUnit(millionCount, "만회");
        } else if (count >= 1000) {
            double thousandCount = count / 1000.0;
            return formatCountWithUnit(thousandCount, "천회");
        } else {
            return Long.toString(count) + "회";
        }
    }

    private static String formatCountWithUnit(double count, String unit) {
        if (count == (long) count) {
            return String.format("%d%s", (long) count, unit);
        } else {
            return String.format("%.1f%s", count, unit);
        }
    }

    public static String formatPlayTime(Long seconds) {
        if(seconds == null) {
            return null;
        }
        long minutes = seconds / 60;
        long remainingSeconds = seconds % 60;

        if (minutes < 1) {
            return String.format("0:%02d", remainingSeconds);
        } else if (minutes < 10) {
            return String.format("%d:%02d", minutes, remainingSeconds);
        } else {
            long hours = minutes / 60;
            long remainingMinutes = minutes % 60;
            return String.format("%d:%02d:%02d", hours, remainingMinutes, remainingSeconds);
        }
    }


    @Transactional
    public Video saveVideo(JSONObject crawlingResponse, User user, String pageUrl) {

        Video video = Video.builder().user(user).pageUrl(pageUrl)
                .title(Optional.ofNullable(crawlingResponse.get("title")).map(Object::toString)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(crawlingResponse.get("thumbnail_url"))
                        .map(Object::toString).orElse(null))
                .description(Optional.ofNullable(crawlingResponse.get("description"))
                        .map(Object::toString).orElse(null))
                .embedUrl(
                        Optional.ofNullable(crawlingResponse.get("embed_url")).map(Object::toString)
                                .orElse(null))
                .channelName(Optional.ofNullable(crawlingResponse.get("channel_name"))
                        .map(Object::toString)
                        .orElse(null))
                .channelImageUrl(Optional.ofNullable(crawlingResponse.get("channel_image_url"))
                        .map(Object::toString).orElse(null))
                .watchedCnt(Optional.ofNullable(crawlingResponse.get("watched_cnt"))
                        .map(Object::toString)
                        .map(Long::parseLong).orElse(null))
                .playTime(
                        Optional.ofNullable(crawlingResponse.get("play_time")).map(Object::toString)
                                .map(Long::parseLong).orElse(null))
                .publishedDate(Optional.ofNullable(crawlingResponse.get("published_date"))
                        .map(Object::toString).map(Long::parseLong)
                        .map(TimeService::fromUnixTime).orElse(null))
                .siteName(
                        Optional.ofNullable(crawlingResponse.get("site_name")).map(Object::toString)
                                .orElse(null))
                .build();

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

    @Transactional
    public Long getVideoCount(String email) {
        User user = userService.validateUser(email);
        return videoRepository.countByUserAndDeletedDateIsNull(user);
    }

    @Transactional
    public Slice<GetVideoResponse> getVideos(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        Slice<Video> videoSlice = videoRepository.findAllByUserAndDeletedDateIsNull(user,
                pageRequest).orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP));

        return videoSlice.map(GetVideoResponse::of);
    }
}
