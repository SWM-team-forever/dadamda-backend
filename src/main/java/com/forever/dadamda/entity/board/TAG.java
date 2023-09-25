package com.forever.dadamda.entity.board;

import lombok.Getter;

@Getter
public enum TAG {
    ENTERTAINMENT_ART("엔터테인먼트, 예술"),
    LIFE_SHOPPING("생활, 노하우, 쇼핑"),
    HOBBY_TRAVEL("취미, 여가, 여행"),
    KNOWLEDGE_TREND("지식, 동향");

    private final String tagDescription;

    TAG(String tagDescription){
        this.tagDescription = tagDescription;
    }
}
