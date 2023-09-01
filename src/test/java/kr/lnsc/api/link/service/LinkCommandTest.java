package kr.lnsc.api.link.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.exception.CanNotFoundUniquePathException;
import kr.lnsc.api.link.repository.LinkRepository;
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

    @DisplayName("요청한 정보로 Link 객체를 생성한다.")
    @Test
    void createLink() {
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_NEXT_DAY_EXPIRED.originalUrl, EXAMPLE_NEXT_DAY_EXPIRED.expireDate);
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
                () -> "12345678"
        );
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.originalUrl, EXAMPLE_TODAY_EXPIRED.expireDate);

        customLinkCommand.createLink(request);

        assertThatThrownBy(() -> customLinkCommand.createLink(request))
                .isInstanceOf(CanNotFoundUniquePathException.class);
    }
}