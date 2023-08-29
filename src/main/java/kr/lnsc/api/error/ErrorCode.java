package kr.lnsc.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {
    UNHANDLED_ERROR("500-01");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }
}
