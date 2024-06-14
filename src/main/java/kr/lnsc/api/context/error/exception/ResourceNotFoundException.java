package kr.lnsc.api.context.error.exception;

import kr.lnsc.api.context.error.ErrorCode;

public abstract class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }
}
