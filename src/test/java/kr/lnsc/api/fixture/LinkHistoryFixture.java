package kr.lnsc.api.fixture;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

public enum LinkHistoryFixture {
    ACCESS_TODAY(LocalDateTime.now()),
    ACCESS_YESTERDAY(LocalDateTime.now().minusDays(1)),
    ACCESS_LAST_WEEK(LocalDateTime.now().minusWeeks(1));

    public final LocalDateTime accessedAt;

    LinkHistoryFixture(LocalDateTime accessedAt) {
        this.accessedAt = accessedAt;
    }

    public LinkHistory toLinkHistory(Link link) {
        LinkHistory newLinkHistory = LinkHistory.from(link);
        ReflectionTestUtils.setField(newLinkHistory, "createdAt", accessedAt);
        return newLinkHistory;
    }
}
