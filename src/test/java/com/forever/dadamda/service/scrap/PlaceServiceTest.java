package com.forever.dadamda.service.scrap;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.forever.dadamda.dto.scrap.place.GetPlaceResponse;
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

    String email = "1234@naver.com";

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
}
