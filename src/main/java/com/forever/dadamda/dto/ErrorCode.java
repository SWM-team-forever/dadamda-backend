package com.forever.dadamda.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {
    /**
     * 400 Bad Request (잘못된 요청)
     */
    INVALID("BR000", "잘못된 요청입니다."),

    INVALID_AUTH_TOKEN("BR001", "만료되거나 잘못된 엑세스 토큰입니다."),
    INVALID_DUPLICATED_SCRAP("BR002","이미 저장된 URL입니다."),
    INVALID_DUPLICATED_NICKNAME("BR003", "이미 사용중인 닉네임입니다."),
    INVALID_HEART("BR004", "좋아요를 누르지 않은 글입니다."),
    INVALID_AUTHENTICATION_TO_PUBLISH("BR005", "게시 권한이 없습니다."),

    /**
     * 404 Not Found (존재하지 않는 리소스)
     */
    NOT_EXISTS("NF000", "존재하지 않습니다."),
    NOT_EXISTS_SCRAP("NF001", "존재하지 않는 스크랩입니다."),
    NOT_EXISTS_MEMBER("NF002", "존재하지 않는 회원입니다."),
    NOT_EXISTS_MEMO("NF003", "존재하지 않는 메모입니다."),
    NOT_EXISTS_BOARD_TAG("NF004", "존재하지 않는 보드 태그입니다."),
    NOT_EXISTS_BOARD("NF005", "존재하지 않는 보드입니다."),

    /**
     * 500 Internal Server Exception (서버 내부 에러)
     */
    INTERNAL_SERVER("IS000", "서버 내부 에러가 발생했습니다."),
    ;

    private final String code;
    private final String message;

    ErrorCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
