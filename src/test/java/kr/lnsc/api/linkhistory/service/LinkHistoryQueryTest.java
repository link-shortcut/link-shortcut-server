package kr.lnsc.api.linkhistory.service;

import kr.lnsc.api.fixture.LinkHistoryFixture;
import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import kr.lnsc.api.linkhistory.dto.LinkAccessCountDto;
import kr.lnsc.api.util.ServiceTest;
import kr.lnsc.api.util.TestLinkHistoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static kr.lnsc.api.fixture.LinkHistoryFixture.*;
import static org.assertj.core.api.Assertions.assertThat;

@Import(TestLinkHistoryRepository.class)
class LinkHistoryQueryTest extends ServiceTest {
    private static Link link;

    @Autowired
    private LinkHistoryQuery linkHistoryQuery;

    @Autowired
    private TestLinkHistoryRepository testLinkHistoryRepository;

    @BeforeEach
    void setUp() {
        link = EXAMPLE_TODAY_EXPIRED.toLink();
        linkRepository.save(link);

        Arrays.stream(LinkHistoryFixture.values())
                .forEach(lhf -> {
                    LinkHistory savedLinkHistory = linkHistoryRepository.save(lhf.toLinkHistory(link));
                    testLinkHistoryRepository.updateAccessedAt(savedLinkHistory, lhf.accessedAt);
                });
    }

    @DisplayName("단축 URL 접속 횟수를 특정 일자 기준으로 묶어 오름차순으로 반환한다.")
    @Test
    void getAccessCountByDate() {
        List<LinkAccessCountDto> accessCountByDate = linkHistoryQuery.getAccessCountByDate(link);

        List<LocalDate> expectedAccessedDate = Stream.of(ACCESS_LAST_WEEK, ACCESS_YESTERDAY, ACCESS_TODAY)
                .map(lhf -> lhf.accessedAt)
                .map(LocalDateTime::toLocalDate)
                .toList();
        assertThat(accessCountByDate).hasSize(3);
        assertThat(accessCountByDate).extracting("date").containsExactlyElementsOf(expectedAccessedDate);
        assertThat(accessCountByDate).extracting("count").containsOnly(1L);
    }

    @DisplayName("특정 일자의 단축 URL 접속 횟수를 반환한다.")
    @Test
    void getSpecificDateAccessCount() {
        long todayAccessCount = linkHistoryQuery.getAccessCount(link, LocalDate.now());

        assertThat(todayAccessCount).isEqualTo(1L);
    }

    @DisplayName("단축 URL의 전체 접속 횟수를 반환한다.")
    @Test
    void getTotalAccessCount() {
        long totalAccessCount = linkHistoryQuery.getTotalAccessCount(link);

        assertThat(totalAccessCount).isEqualTo(3L);
    }
}