package kr.lnsc.api.ratelimit.exception;

import kr.lnsc.api.error.ErrorCode;
import kr.lnsc.api.error.exception.BusinessException;

public class RateLimitExceededException extends BusinessException {
    private static final String MESSAGE = "단기간에 너무 많은 요청을 보냈습니다. 잠시후 다시 요청해주세요.";

    public RateLimitExceededException() {
        super(MESSAGE, ErrorCode.TOO_MANY_REQUESTS);
    }
}
