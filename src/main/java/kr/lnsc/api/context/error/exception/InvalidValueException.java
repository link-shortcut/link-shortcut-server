package kr.lnsc.api.context.error.exception;

import kr.lnsc.api.context.error.ErrorCode;

public abstract class InvalidValueException extends BusinessException {
    public InvalidValueException(String message, ErrorCode code) {
        super(message, code);
    }
}
