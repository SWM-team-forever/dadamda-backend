package com.forever.dadamda.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class RandomServiceTest {

    int maxWordLength = 3;

    @Test
    void should_adjectives_array_have_no_duplicate_values_and_length_equal_to_the_adjectivesLength() {
        // adjectives 배열은 중복된 값이 없어야 하며, 길이가 adjectivesLength와 같아야 한다.
        String[] adjectives = RandomService.adjectives;
        Set<String> adjectiveSet = new HashSet<>(List.of(adjectives));

        assertThat(adjectiveSet.size()).isEqualTo(adjectives.length);
        assertThat(adjectiveSet.size()).isEqualTo(RandomService.adjectivesLength);
    }

    @Test
    void should_animals_array_have_no_duplicate_values_and_length_equal_to_the_animalsLength() {
        // animals 배열은 중복된 값이 없어야 하며, 길이가 animalsLength와 같아야 한다.
        //given
        //when
        String[] animals = RandomService.animals;
        Set<String> animalSet = new HashSet<>(List.of(animals));

        //then
        assertThat(animalSet.size()).isEqualTo(animals.length);
        assertThat(animalSet.size()).isEqualTo(RandomService.animalsLength);
    }

    @Test
    void should_the_maximum_string_length_of_the_adjectives_array_is_maxWordLength() {
        // adjectives 배열의 문자열 최대 길이는 3이다.
        String[] adjectives = RandomService.adjectives;

        int adjectiveMaxLength = 0;

        for(String adjective : adjectives) {
            if(adjectiveMaxLength < adjective.length()) {
                adjectiveMaxLength = adjective.length();
            }
        }

        assertThat(adjectiveMaxLength).isLessThanOrEqualTo(maxWordLength);
    }

    @Test
    void should_the_maximum_string_length_of_the_animals_array_is_maxWordLength() {
        // animals 배열의 문자열 최대 길이는 3이다.
        String[] animals = RandomService.adjectives;

        int animalMaxLength = 0;

        for(String animal : animals) {
            if(animalMaxLength < animal.length()) {
                animalMaxLength = animal.length();
            }
        }

        assertThat(animalMaxLength).isLessThanOrEqualTo(maxWordLength);
    }

    @Test
    void should_the_maximum_length_of_the_nickname_is_10_when_generating_a_random_nickname() {
        // 랜덤 닉네임을 생성할때, 닉네임의 최대 길이는 10이다.
        //given
        //when
        String nickname = RandomService.generateRandomNickname();

        //then
        assertThat(nickname.length()).isLessThanOrEqualTo(10);
    }
}
