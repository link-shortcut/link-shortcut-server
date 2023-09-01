package kr.lnsc.api.link.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;

class LinkTest {

    @DisplayName("새로운 Link 생성에 성공한다.")
    @Test
    void createLinkSuccess() {
        Link newLink = Link.createLink(EXAMPLE_TODAY_EXPIRED.originalUrl, EXAMPLE_TODAY_EXPIRED.shortenPath, EXAMPLE_TODAY_EXPIRED.expireKey, EXAMPLE_TODAY_EXPIRED.expireDate);

        assertThat(newLink.getOriginalUrl()).isEqualTo(EXAMPLE_TODAY_EXPIRED.originalUrl);
        assertThat(newLink.getShortenPath()).isEqualTo(EXAMPLE_TODAY_EXPIRED.shortenPath);
        assertThat(newLink.getExpireKey()).isEqualTo(EXAMPLE_TODAY_EXPIRED.expireKey);

        LocalDateTime expiredAt = newLink.getExpiredAt();
        assertThat(expiredAt.toLocalDate()).isEqualTo(EXAMPLE_TODAY_EXPIRED.expireDate.plusDays(1));
        assertThat(expiredAt.toLocalTime()).isEqualTo(LocalTime.MIN);
    }
}