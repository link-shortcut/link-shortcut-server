package kr.lnsc.api.domain.link;

import kr.lnsc.api.domain.link.exception.InvalidUrlException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlTest {
    private static final String VALID_URL = "http://www.example.com";

    @DisplayName("URL 생성에 성공한다.")
    @Test
    void createUrlSuccess() {
        new Url(VALID_URL);
    }

    private static Stream<Arguments> invalidUrls() {
        return Stream.of(
                Arguments.of(""),
                Arguments.of(VALID_URL + "a".repeat(2048)),
                Arguments.of(VALID_URL + "/aa!@##$"),
                Arguments.of(VALID_URL + "/aaa?abcd,gef")
        );
    }

    @DisplayName("유효하지 않은 URL일 경우 예외가 발생한다.")
    @ParameterizedTest
    @NullSource
    @MethodSource("invalidUrls")
    void invalidUrlFail(String invalidUrl) {
        assertThatThrownBy(() -> new Url(invalidUrl))
                .isInstanceOf(InvalidUrlException.class);
    }
}