package kr.lnsc.api.system.link.application;

import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.link.domain.generator.ShortenPathGenerator;
import kr.lnsc.api.system.link.exception.CanNotFoundUniquePathException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.anyString;
import static org.mockito.BDDMockito.given;

@MockitoSettings
class UniqueLinkCreatorTest {
    private static final String SHORTEN_PATH = "aaaaaaa";

    @InjectMocks
    private UniqueLinkCreator uniqueLinkCreator;

    @Mock
    private LinkRepository linkRepository;

    @Mock
    private ShortenPathGenerator shortenPathGenerator;

    @DisplayName("고유한 단축 Path를 생성한다.")
    @Test
    void createSuccess() {
        // given
        given(shortenPathGenerator.generate()).willReturn(SHORTEN_PATH);
        given(linkRepository.existsByShortenPath(anyString())).willReturn(false);

        // when
        String uniqueShortenPath = uniqueLinkCreator.create();

        // when
        assertThat(uniqueShortenPath).isEqualTo(SHORTEN_PATH);
    }

    @DisplayName("일정 시도 횟수로도 고유한 단축 Path를 생성하지 못하면 예외가 발생한다.")
    @Test
    void createFail() {
        // given
        given(shortenPathGenerator.generate()).willReturn(SHORTEN_PATH);
        given(linkRepository.existsByShortenPath(anyString())).willReturn(true);

        // expected
        assertThatThrownBy(() -> uniqueLinkCreator.create())
                .isInstanceOf(CanNotFoundUniquePathException.class);
    }
}