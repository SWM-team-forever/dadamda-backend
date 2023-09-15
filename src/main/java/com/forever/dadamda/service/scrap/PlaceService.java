package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.dto.scrap.place.GetPlaceResponse;
import com.forever.dadamda.entity.scrap.Place;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.PlaceRepository;
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
public class PlaceService {

    private final PlaceRepository placeRepository;
    private final UserService userService;

    @Transactional
    public Slice<GetPlaceResponse> getPlaces(String email, Pageable pageable) {
        User user = userService.validateUser(email);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");
        PageRequest pageRequest = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(),
                sort);

        return placeRepository.findAllByUserAndDeletedDateIsNull(user, pageRequest)
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_SCRAP))
                .map(GetPlaceResponse::of);
    }

    @Transactional
    public Place savePlace(WebClientBodyResponse crawlingResponse, User user, String pageUrl) {

        Place place = Place.builder()
                .user(user).pageUrl(crawlingResponse.getPageUrl())
                .title(crawlingResponse.getTitle())
                .thumbnailUrl(crawlingResponse.getThumbnailUrl())
                .description(crawlingResponse.getDescription())
                .address(crawlingResponse.getAddress())
                .latitude(crawlingResponse.getLatitude())
                .longitude(crawlingResponse.getLongitude())
                .phoneNumber(crawlingResponse.getPhoneNumber())
                .zipCode(crawlingResponse.getZipCode())
                .homepageUrl(crawlingResponse.getHomepageUrl())
                .category(crawlingResponse.getCategory())
                .siteName(crawlingResponse.getSiteName())
                .build();

        return placeRepository.save(place);
    }

    @Transactional
    public Long getPlaceCount(String email) {
        User user = userService.validateUser(email);
        return placeRepository.countByUserAndDeletedDateIsNull(user);
    }
}
