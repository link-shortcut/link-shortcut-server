package kr.lnsc.api.link.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.request.ExpireShortenLinkRequest;
import kr.lnsc.api.link.exception.CanNotFoundUniquePathException;
import kr.lnsc.api.link.exception.InvalidExpireKeyException;
import kr.lnsc.api.link.repository.LinkRepository;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import kr.lnsc.api.util.ServiceTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.time.LocalTime;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_NEXT_DAY_EXPIRED;
import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinkCommandTest extends ServiceTest {

    @Autowired
    private LinkCommand linkCommand;

    @Autowired
    private LinkRepository linkRepository;

    @Autowired
    private LinkQuery linkQuery;

    @Autowired
    private LinkHistoryCommand linkHistoryCommand;

    @DisplayName("요청한 정보로 Link 객체를 생성한다.")
    @Test
    void createLink() {
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_NEXT_DAY_EXPIRED.originalUrl.getValue(), EXAMPLE_NEXT_DAY_EXPIRED.expireDate);
        Link createdLink = linkCommand.createLink(request);

        Link findLink = linkRepository.findAll().get(0);

        final LocalDateTime TODAY = LocalDateTime.of(EXAMPLE_TODAY_EXPIRED.expireDate, LocalTime.MAX);

        assertThat(linkRepository.findAll()).hasSize(1);
        assertThat(findLink.getId()).isEqualTo(createdLink.getId());
        assertThat(findLink.getOriginalUrl()).isEqualTo(EXAMPLE_NEXT_DAY_EXPIRED.originalUrl);
        assertThat(findLink.getShortenPath()).isNotNull();
        assertThat(findLink.getExpireKey()).isNotNull();
        assertThat(findLink.getExpiredAt()).isAfter(TODAY);
    }

    @DisplayName("일정 시도 횟수로도 고유한 단축 Path를 얻지 못하면 예외가 발생한다.")
    @Test
    void uniqueShortenPathFail() {
        LinkCommand customLinkCommand = new LinkCommand(
                linkRepository,
                () -> "AAAAAAA",
                () -> "12345678",
                linkQuery,
                linkHistoryCommand
        );
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.originalUrl.getValue(), EXAMPLE_TODAY_EXPIRED.expireDate);

        customLinkCommand.createLink(request);

        assertThatThrownBy(() -> customLinkCommand.createLink(request))
                .isInstanceOf(CanNotFoundUniquePathException.class);
    }

    @DisplayName("Link 객체의 만료키와 입력한 만료키가 같을 경우 Link 객체와 LinkHistory 객체를 삭제한다.")
    @Test
    void expireLinkSuccess() {
        Link link = EXAMPLE_NEXT_DAY_EXPIRED.toLink();
        linkRepository.save(link);
        linkHistoryRepository.save(LinkHistory.from(link));

        ExpireShortenLinkRequest request =
                new ExpireShortenLinkRequest(EXAMPLE_NEXT_DAY_EXPIRED.shortenPath, EXAMPLE_NEXT_DAY_EXPIRED.expireKey);

        linkCommand.expireLink(request);

        assertThat(linkRepository.findAll()).hasSize(0);
        assertThat(linkHistoryRepository.findAll()).hasSize(0);
    }

    @DisplayName("Link 객체의 만료키와 입력한 만료키가 다른 경우 예외가 발생한다.")
    @Test
    void unmatchedExpireKeyFail() {
        final String INVALID_EXPIRE_KEY = EXAMPLE_TODAY_EXPIRED.expireKey;

        Link link = EXAMPLE_NEXT_DAY_EXPIRED.toLink();
        linkRepository.save(link);

        ExpireShortenLinkRequest request =
                new ExpireShortenLinkRequest(EXAMPLE_NEXT_DAY_EXPIRED.shortenPath, INVALID_EXPIRE_KEY);

        assertThatThrownBy(() -> linkCommand.expireLink(request))
                .isInstanceOf(InvalidExpireKeyException.class);
    }

    @DisplayName("단축 Path로 Link를 찾은 후 LinkHistory를 생성하고, 해당 Link를 반환한다.")
    @Test
    void accessLinkSuccess() {
        Link link = linkRepository.save(EXAMPLE_TODAY_EXPIRED.toLink());
        Link accessLink = linkCommand.accessLink(EXAMPLE_TODAY_EXPIRED.shortenPath);

        LinkHistory createdLinkHistory = linkHistoryRepository.findAll().get(0);
        assertThat(link.getId()).isEqualTo(accessLink.getId());
        assertThat(createdLinkHistory.getLink().getId()).isEqualTo(link.getId());
    }
}