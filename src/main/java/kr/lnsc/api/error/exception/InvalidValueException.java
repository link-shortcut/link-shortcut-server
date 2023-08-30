package kr.lnsc.api.error.exception;

import kr.lnsc.api.error.ErrorCode;

public abstract class InvalidValueException extends BusinessException {
    public InvalidValueException(String message, ErrorCode code) {
        super(message, code);
    }
}
