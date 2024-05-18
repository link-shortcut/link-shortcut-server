package kr.lnsc.api.system.link.exception;

import kr.lnsc.api.context.error.ErrorCode;
import kr.lnsc.api.context.error.exception.InvalidValueException;

public class InvalidUrlException extends InvalidValueException {
    private static final String MESSAGE = "유효하지 않는 URL입니다. 확인 후 다시 입력해주세요.";

    public InvalidUrlException() {
        super(MESSAGE, ErrorCode.INVALID_URL);
    }
}
