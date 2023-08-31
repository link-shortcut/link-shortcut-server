package kr.lnsc.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import kr.lnsc.api.error.exception.BusinessException;

import java.time.LocalDateTime;

public class ExceptionResponse {

    @JsonFormat
    private final LocalDateTime timestamp = LocalDateTime.now();

    private final String message;

    private final ErrorCode code;

    public ExceptionResponse(String message, ErrorCode code) {
        this.message = message;
        this.code = code;
    }

    public static ExceptionResponse from(BusinessException e) {
        return new ExceptionResponse(e.getMessage(), e.getCode());
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getMessage() {
        return message;
    }

    public String getCode() {
        return code.getValue();
    }
}
