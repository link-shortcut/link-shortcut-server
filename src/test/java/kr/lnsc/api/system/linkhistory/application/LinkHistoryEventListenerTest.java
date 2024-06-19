package kr.lnsc.api.system.linkhistory.application;

import kr.lnsc.api.fixture.LinkFixture;
import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.linkhistory.domain.LinkHistory;
import kr.lnsc.api.system.linkhistory.domain.LinkHistoryRepository;
import kr.lnsc.api.system.linkhistory.domain.event.AppendLinkHistoryEvent;
import kr.lnsc.api.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class LinkHistoryEventListenerTest extends ServiceTest {

    @Autowired
    private LinkHistoryRepository linkHistoryRepository;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @Test
    @DisplayName("LinkHistory를 저장한다.")
    void appendLinkHistory() {
        // given
        Link link = LinkFixture.EXAMPLE_TODAY_EXPIRED.toLink();
        linkRepository.save(link);

        AppendLinkHistoryEvent event = AppendLinkHistoryEvent.from(link.getId());

        // when
        eventPublisher.publishEvent(event);

        // then
        List<LinkHistory> linkHistories = linkHistoryRepository.findAll();
        assertThat(linkHistories).hasSize(1);
        assertThat(linkHistories).extracting("link").extracting("id")
                .contains(link.getId());
    }
}