package kr.lnsc.api.domain.link;

import kr.lnsc.api.link.domain.Url;
import kr.lnsc.api.link.exception.InvalidUrlException;
import kr.lnsc.api.property.BaseUrlProperty;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.NullSource;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class UrlTest {
    private static final String VALID_URL = "http://www.example.com";
    private static final String BASE_URL = "https://www.test.kr";
    private static final String BASE_URL_WITHOUT_WWW = "https://test.kr";

    @BeforeEach
    void setUp() {
        ReflectionTestUtils.setField(BaseUrlProperty.class, "baseUrl", BASE_URL);
    }

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
                Arguments.of(VALID_URL + "/aaa?abcd,gef"),
                Arguments.of(BASE_URL),
                Arguments.of(BASE_URL_WITHOUT_WWW)
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

    @DisplayName("입력된 URL Spec으로부터 금지된 Host들을 가져온다.")
    @Test
    void permittedHosts() {
        List<String> permittedHosts = Url.permittedHosts(VALID_URL);

        assertThat(permittedHosts).containsExactlyInAnyOrder("www.example.com", "example.com");
    }
}