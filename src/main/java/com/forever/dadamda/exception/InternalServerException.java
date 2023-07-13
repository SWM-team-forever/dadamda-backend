package com.forever.dadamda.exception;

import com.forever.dadamda.dto.ErrorCode;

public class InternalServerException extends GeneralException {

    public InternalServerException(String message, ErrorCode errorCode) {
        super(message, errorCode);
    }

    public InternalServerException(String message) {
        super(message, ErrorCode.INTERNAL_SERVER);
    }
}