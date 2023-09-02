package kr.lnsc.api.link.exception;

import kr.lnsc.api.error.ErrorCode;
import kr.lnsc.api.error.exception.ResourceNotFoundException;

public class LinkNotFoundException extends ResourceNotFoundException {
    private static final String MESSAGE = "해당 링크가 이미 만료되었거나 존재하지 않아 불러올 수 없습니다.";

    public LinkNotFoundException() {
        super(MESSAGE, ErrorCode.LINK_NOT_FOUND);
    }
}
