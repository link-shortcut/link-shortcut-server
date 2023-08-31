package kr.lnsc.api.link.service;

import kr.lnsc.api.link.exception.LinkNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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
}