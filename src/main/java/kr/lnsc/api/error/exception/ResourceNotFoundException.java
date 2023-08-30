package kr.lnsc.api.error.exception;

import kr.lnsc.api.error.ErrorCode;

public abstract class ResourceNotFoundException extends BusinessException {
    public ResourceNotFoundException(String message, ErrorCode code) {
        super(message, code);
    }
}
