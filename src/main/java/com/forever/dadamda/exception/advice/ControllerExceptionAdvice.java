package com.forever.dadamda.exception.advice;

import static java.util.stream.Collectors.joining;

import com.forever.dadamda.dto.ApiResponse;
import com.forever.dadamda.dto.ErrorCode;
import com.forever.dadamda.exception.InvalidException;
import com.forever.dadamda.exception.NotFoundException;
import io.sentry.Sentry;
import javax.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RequiredArgsConstructor
@RestControllerAdvice
public class ControllerExceptionAdvice {

    /**
     * 400 Bad Request (잘못된 요청, Validation Exception)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BindException.class)
    private ApiResponse<Object> handleValidationError(BindException e) {
        String errorMessage = e.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .collect(joining("\n"));
        return ApiResponse.error(ErrorCode.INVALID, errorMessage);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    private ApiResponse<Object> handleValidationParameterError(ConstraintViolationException e) {
        String errorMessage = e.getMessage();
        return ApiResponse.error(ErrorCode.INVALID, errorMessage);
    }

    /**
     * 400 Bad Request (잘못된 요청)
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidException.class)
    private ApiResponse<Object> handleBadRequest(InvalidException e) {
        return ApiResponse.error(e.getErrorCode());
    }

    /**
     * 404 Not Found (존재하지 않는 리소스)
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException.class)
    private ApiResponse<Object> handleNotFound(NotFoundException e) {
        return ApiResponse.error(e.getErrorCode());
    }

    /**
     * 500 Internal Server Exception (서버 내부 에러)
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    private ApiResponse<Object> handleInternalServerException(Exception e) {
        Sentry.captureException(e);
        return ApiResponse.error(ErrorCode.INTERNAL_SERVER, e.getMessage());
    }
}
