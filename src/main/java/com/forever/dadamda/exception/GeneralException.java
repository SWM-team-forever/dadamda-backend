package com.forever.dadamda.exception;

import com.forever.dadamda.dto.ErrorCode;
import lombok.Getter;

@Getter
public abstract class GeneralException extends RuntimeException {

    private final ErrorCode errorCode;

    protected GeneralException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}
