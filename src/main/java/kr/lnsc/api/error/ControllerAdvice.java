package kr.lnsc.api.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    private static final String UNHANDLED_EXCEPTION_MESSAGE = "알 수 없는 오류가 발생했습니다. 관리자에게 연락해주세요.";

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleUnhandledException(Exception e) {
        log.error("handleUnhandledException", e);
        return new ExceptionResponse(UNHANDLED_EXCEPTION_MESSAGE, ErrorCode.UNHANDLED_ERROR);
    }
}
