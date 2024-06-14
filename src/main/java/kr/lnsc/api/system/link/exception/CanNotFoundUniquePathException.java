package kr.lnsc.api.system.link.exception;

import kr.lnsc.api.context.error.ErrorCode;
import kr.lnsc.api.context.error.exception.BusinessException;

public class CanNotFoundUniquePathException extends BusinessException {
    private static final String MESSAGE = "생성 가능한 단축 URL 탐색에 실패했습니다. 다시 시도해주세요.";

    public CanNotFoundUniquePathException() {
        super(MESSAGE, ErrorCode.CAN_NOT_FOUND_UNIQUE_PATH);
    }
}
