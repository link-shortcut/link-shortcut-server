package kr.lnsc.api.link.exception;

import kr.lnsc.api.error.ErrorCode;
import kr.lnsc.api.error.exception.InvalidValueException;

public class InvalidExpireKeyException extends InvalidValueException {
    private static final String MESSAGE = "만료키가 일치하지 않거나 유효하지 않는 만료키입니다. 확인 후 다시 입력해주세요.";

    public InvalidExpireKeyException() {
        super(MESSAGE, ErrorCode.INVALID_REQUEST_VALUE);
    }
}
