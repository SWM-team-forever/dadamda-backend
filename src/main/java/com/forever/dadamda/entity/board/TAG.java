package com.forever.dadamda.entity.board;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.exception.NotFoundException;
import java.util.Arrays;
import lombok.Getter;

@Getter
public enum TAG {
    ENTERTAINMENT_ART("엔터테인먼트/예술"),
    LIFE_SHOPPING("생활/노하우/쇼핑"),
    HOBBY_TRAVEL("취미/여가/여행"),
    KNOWLEDGE_TREND("지식/동향");

    private final String tagDescription;

    TAG(String tagDescription){
        this.tagDescription = tagDescription;
    }

    @JsonCreator
    public static TAG from(String inputTag){
        return Arrays.stream(TAG.values())
                .filter(tag -> tag.name().equals(inputTag.toUpperCase()))
                .findFirst()
                .orElseThrow(() -> new NotFoundException(ErrorCode.NOT_EXISTS_BOARD_TAG));
    }
}
