package kr.lnsc.api.link.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.exception.LinkNotFoundException;
import kr.lnsc.api.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_ALREADY_EXPIRED;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinkQueryTest extends ServiceTest {

    @Autowired
    private LinkQuery linkQuery;

    @DisplayName("단축 Path에 해당하는 Link가 없을때 예외를 발생시킨다.")
    @Test
    void notExistLinkFindFail() {
        final String INVALID_SHORTEN_PATH = "aaaaaaa";

        assertThatThrownBy(() -> linkQuery.getLink(INVALID_SHORTEN_PATH))
                .isInstanceOf(LinkNotFoundException.class);
    }

    @DisplayName("단축 Path에 해당하는 Link가 만료일이 요청 시점보다 이전일때 예외를 발생시킨다.")
    @Test
    void expiredLinkFindFail() {
        final Link expiredLink = EXAMPLE_ALREADY_EXPIRED.toLink();
        linkRepository.save(expiredLink);

        assertThatThrownBy(() -> linkQuery.getLink(EXAMPLE_ALREADY_EXPIRED.shortenPath))
                .isInstanceOf(LinkNotFoundException.class);
    }
}