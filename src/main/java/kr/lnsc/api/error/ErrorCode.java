package kr.lnsc.api.error;

import lombok.Getter;

@Getter
public enum ErrorCode {

    /* 400 */
    INVALID_URL("400-01"),

    /* 404 */
    LINK_NOT_FOUND("404-01"),

    /* 500 */
    UNHANDLED_ERROR("500-01"),
    CAN_NOT_FOUND_UNIQUE_PATH("500-02");

    private final String value;

    ErrorCode(String value) {
        this.value = value;
    }
}
