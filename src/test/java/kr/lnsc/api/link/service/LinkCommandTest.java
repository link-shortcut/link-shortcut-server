package kr.lnsc.api.link.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.domain.Url;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.exception.CanNotFoundUniquePathException;
import kr.lnsc.api.link.repository.LinkRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class LinkCommandTest extends ServiceTest {
    private static final Url ORIGINAL_URL = new Url("http://www.example.com");

    @Autowired
    private LinkCommand linkCommand;

    @Autowired
    private LinkRepository linkRepository;

    @DisplayName("요청한 정보로 Link 객체를 생성한다.")
    @Test
    void createLink() {
        final LocalDateTime NEXT_DAY = NOW.plusDays(1);

        CreateShortenLinkRequest request = new CreateShortenLinkRequest(ORIGINAL_URL, NEXT_DAY.toLocalDate());
        Link createdLink = linkCommand.createLink(request);

        Link findLink = linkRepository.findAll().get(0);

        assertThat(linkRepository.findAll()).hasSize(1);
        assertThat(findLink.getId()).isEqualTo(createdLink.getId());
        assertThat(findLink.getOriginalUrl()).isEqualTo(ORIGINAL_URL);
        assertThat(findLink.getShortenPath()).isNotNull();
        assertThat(findLink.getExpiredKey()).isNotNull();
        assertThat(findLink.getCreatedAt()).isAfter(NOW);
    }

    @DisplayName("일정 시도 횟수로도 고유한 단축 Path를 얻지 못하면 예외가 발생한다.")
    @Test
    void uniqueShortenPathFail() {
        LinkCommand customLinkCommand = new LinkCommand(
                linkRepository,
                () -> "AAAAAAA",
                () -> "12345678"
        );
        CreateShortenLinkRequest request = new CreateShortenLinkRequest(ORIGINAL_URL, NOW.toLocalDate());

        customLinkCommand.createLink(request);

        assertThatThrownBy(() -> customLinkCommand.createLink(request))
                .isInstanceOf(CanNotFoundUniquePathException.class);
    }
}