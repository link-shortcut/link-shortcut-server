package kr.lnsc.api.error;

import kr.lnsc.api.error.exception.InvalidValueException;
import kr.lnsc.api.error.exception.ResourceNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ControllerAdvice {
    private static final String UNHANDLED_EXCEPTION_MESSAGE = "알 수 없는 오류가 발생했습니다. 관리자에게 연락해주세요.";

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ExceptionResponse handleMethodArgumentException(BindingResult bindingResult) {
        String errorMessage = bindingResult.getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList()
                .get(0);
        return new ExceptionResponse(errorMessage, ErrorCode.INVALID_REQUEST_VALUE);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public ExceptionResponse handleResourceNotFoundException(ResourceNotFoundException e) {
        log.error("handleResourceNotFoundException", e);
        return ExceptionResponse.from(e);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(InvalidValueException.class)
    public ExceptionResponse handleInvalidValueException(InvalidValueException e) {
        log.error("handleInvalidValueException", e);
        return ExceptionResponse.from(e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public ExceptionResponse handleUnhandledException(Exception e) {
        log.error("handleUnhandledException", e);
        return new ExceptionResponse(UNHANDLED_EXCEPTION_MESSAGE, ErrorCode.UNHANDLED_ERROR);
    }
}
