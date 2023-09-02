package kr.lnsc.api.linkhistory.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import kr.lnsc.api.util.ServiceTest;
import org.assertj.core.api.Fail;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;

class LinkHistoryCommandTest extends ServiceTest {

    @Autowired
    private LinkHistoryCommand linkHistoryCommand;

    @DisplayName("Link로 LinkHistory 객체를 생성한다.")
    @Test
    void createHistorySuccess() {
        Link link = linkRepository.save(EXAMPLE_TODAY_EXPIRED.toLink());
        LinkHistory createdLinkHistory = linkHistoryCommand.createHistory(link);

        LinkHistory findLinkHistory = linkHistoryRepository.findById(createdLinkHistory.getId())
                .orElseGet(() -> Fail.fail("해당 LinkHistory가 존재하지 않습니다."));
        assertThat(findLinkHistory.getId()).isEqualTo(createdLinkHistory.getId());
        assertThat(findLinkHistory.getLink().getId()).isEqualTo(link.getId());
    }

    @DisplayName("단축 Path로 Link를 찾은 후 LinkHistory를 생성하고, 해당 Link를 반환한다.")
    @Test
    void accessLinkSuccess() {
        Link link = linkRepository.save(EXAMPLE_TODAY_EXPIRED.toLink());
        Link accessLink = linkHistoryCommand.accessLink(EXAMPLE_TODAY_EXPIRED.shortenPath);

        LinkHistory createdLinkHistory = linkHistoryRepository.findAll().get(0);
        assertThat(link.getId()).isEqualTo(accessLink.getId());
        assertThat(createdLinkHistory.getLink().getId()).isEqualTo(link.getId());
    }
}