package kr.lnsc.api.link.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class LinkTest {

    @DisplayName("새로운 Link 생성에 성공한다.")
    @Test
    void createLinkSuccess() {
        final Url ORIGINAL_URL = new Url("http://www.example.com");
        final String SHORTEN_PATH = "aaaaaaa";
        final String EXPIRE_KEY = "12345678";
        final LocalDate TODAY = LocalDate.now();

        Link newLink = Link.createLink(ORIGINAL_URL, SHORTEN_PATH, EXPIRE_KEY, TODAY);

        assertThat(newLink.getOriginalUrl()).isEqualTo(ORIGINAL_URL);
        assertThat(newLink.getShortenPath()).isEqualTo(SHORTEN_PATH);
        assertThat(newLink.getExpiredKey()).isEqualTo(EXPIRE_KEY);

        LocalDateTime expiredAt = newLink.getExpiredAt();
        assertThat(expiredAt.toLocalDate()).isEqualTo(TODAY);
        assertThat(expiredAt.toLocalTime()).isEqualTo(LocalTime.MAX);
    }
}