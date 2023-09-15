package com.forever.dadamda.service.scrap;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.forever.dadamda.dto.scrap.place.GetPlaceResponse;
import com.forever.dadamda.dto.webClient.WebClientBodyResponse;
import com.forever.dadamda.entity.scrap.Place;
import com.forever.dadamda.entity.user.User;
import com.forever.dadamda.repository.UserRepository;
import com.forever.dadamda.repository.scrap.PlaceRepository;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.Sql.ExecutionPhase;

@SpringBootTest
@ActiveProfiles("test")
@Sql(scripts = "/truncate.sql", executionPhase = ExecutionPhase.AFTER_TEST_METHOD)
@Sql(scripts = "/place-setup.sql", executionPhase = ExecutionPhase.BEFORE_TEST_METHOD)
public class PlaceServiceTest {

    @Autowired
    private PlaceService placeService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PlaceRepository placeRepository;

    private final String email = "1234@naver.com";
    private final String existingPageUrl = "https://www.kakao.map.com/maps/place/kakao";
    private final String changedPageUrl = "https://www.kakao.map.com/maps/place/kakao2";


    @Test
    void should_the_precision_of_latitude_and_longitude_are_expressed_up_to_14_digits_When_getting_place_list() {
        // 장소 조회시, 장소의 위도 경도가 14자리까지 표현되는지 확인
        //given
        //when
        Slice<GetPlaceResponse> getPlaceResponses = placeService.getPlaces(email,
                PageRequest.of(0, 10));

        BigDecimal latitude = getPlaceResponses.getContent().get(0).getLatitude();
        BigDecimal longitude = getPlaceResponses.getContent().get(0).getLongitude();

        //then
        assertEquals(latitude.scale(), 14);
        assertEquals(latitude.precision(), 16);

        assertEquals(longitude.scale(), 14);
        assertEquals(longitude.precision(), 17);
    }

    @Test
    void should_it_is_saved_as_the_first_scrap_When_storing_place_in_a_blank_scrap_db() {
        // 아무 것도 없는 DB에 장소 스크랩 저장시, DB에 첫 번째 스크랩으로 저장되는지 확인
        //given
        placeRepository.deleteAll();

        User user = userRepository.findById(1L).get();

        WebClientBodyResponse webClientBodyResponse = WebClientBodyResponse.builder()
                .pageUrl(existingPageUrl).title("서울역 1호선").address("서울 용산구 한강대로 405 (우)04320")
                .latitude(new BigDecimal("37.422")).longitude(new BigDecimal("-122.084"))
                .phoneNumber("02-1544-7788").build();

        //when
        Place place = placeService.savePlace(webClientBodyResponse, user, existingPageUrl);

        //then
        assertThat(place.getTitle()).isEqualTo(placeRepository.findById(1L).get().getTitle());
    }

    @Test
    void should_it_is_saved_as_pageUrl_received_through_WebClientBodyResponse_When_saving_a_place() {
        // WebClientBodyResponse를 통해 받은 pageUrl로 저장되는지 확인
        //given
        placeRepository.deleteAll();

        User user = userRepository.findById(1L).get();

        WebClientBodyResponse webClientBodyResponse = WebClientBodyResponse.builder()
                .pageUrl(changedPageUrl).title("서울역 1호선").address("서울 용산구 한강대로 405 (우)04320")
                .latitude(new BigDecimal("37.422")).longitude(new BigDecimal("-122.084"))
                .phoneNumber("02-1544-7788").build();

        //when
        Place place = placeService.savePlace(webClientBodyResponse, user, existingPageUrl);

        //then
        assertThat(place.getPageUrl()).isEqualTo(changedPageUrl);
    }
}
