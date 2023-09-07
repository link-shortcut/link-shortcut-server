package kr.lnsc.api.error;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import kr.lnsc.api.error.exception.BusinessException;

import java.time.LocalDateTime;

public class ExceptionResponse {

    @Schema(description = "서버 시간 기준 예외 발생 시간")
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime timestamp = LocalDateTime.now();

    @Schema(description = "예외 메시지")
    private final String message;

    @Schema(description = "에러 코드")
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
