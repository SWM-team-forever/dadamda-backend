package com.forever.dadamda.service.scrap;

import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.dto.scrap.place.GetPlaceResponse;
import com.forever.dadamda.entity.scrap.Place;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.exception.NotFoundException;
import com.forever.dadamda.repository.scrap.PlaceRepository;
import com.forever.dadamda.service.user.UserService;
import java.math.BigDecimal;
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
    public Place savePlace(JSONObject crawlingResponse, User user, String pageUrl) {

        Place place = Place.builder()
                .user(user).pageUrl(pageUrl)
                .title(Optional.ofNullable(crawlingResponse.get("title")).map(Object::toString)
                        .orElse(null))
                .thumbnailUrl(Optional.ofNullable(crawlingResponse.get("thumbnail_url"))
                        .map(Object::toString).orElse(null))
                .description(Optional.ofNullable(crawlingResponse.get("description"))
                        .map(Object::toString)
                        .orElse(null))
                .address(Optional.ofNullable(crawlingResponse.get("address")).map(Object::toString)
                        .orElse(null))
                .latitude(Optional.ofNullable(crawlingResponse.get("lat")).map(
                                lat -> new BigDecimal(lat.toString()))
                        .orElse(null))
                .longitude(Optional.ofNullable(crawlingResponse.get("lng"))
                        .map(lng -> new BigDecimal(lng.toString()))
                        .orElse(null))
                .phoneNumber(Optional.ofNullable(crawlingResponse.get("phonenum"))
                        .map(Object::toString)
                        .orElse(null))
                .zipCode(Optional.ofNullable(crawlingResponse.get("zipcode")).map(Object::toString)
                        .orElse(null))
                .homepageUrl(Optional.ofNullable(crawlingResponse.get("homepage"))
                        .map(Object::toString)
                        .orElse(null))
                .category(
                        Optional.ofNullable(crawlingResponse.get("category")).map(Object::toString)
                                .orElse(null))
                .siteName(
                        Optional.ofNullable(crawlingResponse.get("site_name")).map(Object::toString)
                                .orElse(null))
                .build();

        return placeRepository.save(place);
    }
}
