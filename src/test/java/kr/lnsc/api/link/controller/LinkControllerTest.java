package kr.lnsc.api.link.controller;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.request.ExpireShortenLinkRequest;
import kr.lnsc.api.link.service.LinkCommand;
import kr.lnsc.api.link.service.LinkQuery;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import kr.lnsc.api.linkhistory.service.LinkHistoryQuery;
import kr.lnsc.api.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LinkControllerTest extends ControllerTest {

    @MockBean
    private LinkCommand linkCommand;

    @MockBean
    private LinkQuery linkQuery;

    @MockBean
    private LinkHistoryCommand linkHistoryCommand;

    @MockBean
    private LinkHistoryQuery linkHistoryQuery;

    @DisplayName("입력한 값을 바탕으로 단축 URL을 생성한다.")
    @Test
    void createShortenLink() throws Exception {
        CreateShortenLinkRequest request =
                new CreateShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.originalUrl, EXAMPLE_TODAY_EXPIRED.expireDate);

        given(linkCommand.createLink(any(CreateShortenLinkRequest.class)))
                .willReturn(EXAMPLE_TODAY_EXPIRED.toLink());

        LocalDateTime expectedExpiredAt =
                LocalDateTime.of(EXAMPLE_TODAY_EXPIRED.expireDate.plusDays(1), LocalTime.MIN);
        mockMvc.perform(
                        post("/api/v1/create")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenUrl")
                        .value("https://www.test.kr/" + EXAMPLE_TODAY_EXPIRED.shortenPath))
                .andExpect(jsonPath("$.expiredAt")
                        .value(toIsoLocalDateTime(expectedExpiredAt)))
                .andExpect(jsonPath("$.expireKey").value(EXAMPLE_TODAY_EXPIRED.expireKey));
    }

    @DisplayName("단축 URL로 접속 요쳥시 원래 URL로 리다이렉트한다.")
    @Test
    void accessLink() throws Exception {
        given(linkHistoryCommand.accessLink(anyString()))
                .willReturn(EXAMPLE_TODAY_EXPIRED.toLink());

        mockMvc.perform(get("/{shortenPath}", EXAMPLE_TODAY_EXPIRED.shortenPath)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is3xxRedirection())
                .andExpect(header().string(HttpHeaders.LOCATION, EXAMPLE_TODAY_EXPIRED.originalUrl.getValue()));
    }

    @DisplayName("단축 URL의 통계 정보를 반환한다.")
    @Test
    void getLinkStatisticalInformation() throws Exception {
        Link link = EXAMPLE_TODAY_EXPIRED.toLink();
        LocalDateTime now = LocalDateTime.now();
        ReflectionTestUtils.setField(link, "createdAt", now);

        given(linkQuery.getLink(anyString()))
                .willReturn(link);
        given(linkHistoryQuery.getAccessCount(any(Link.class), any(LocalDate.class)))
                .willReturn(0L);
        given(linkHistoryQuery.getTotalAccessCount(any(Link.class)))
                .willReturn(2L);

        LocalDateTime expectedExpiredAt =
                LocalDateTime.of(EXAMPLE_TODAY_EXPIRED.expireDate.plusDays(1), LocalTime.MIN);

        mockMvc.perform(get("/{shortenPath}/info", EXAMPLE_TODAY_EXPIRED.shortenPath)
                        .contentType(APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful())
                .andExpect(jsonPath("$.originalUrl").value("http://www.example.com"))
                .andExpect(jsonPath("$.dailyAccessCount").value(0L))
                .andExpect(jsonPath("$.totalAccessCount").value(2L))
                .andExpect(jsonPath("$.expiredAt").value(toIsoLocalDateTime(expectedExpiredAt)))
                .andExpect(jsonPath("$.createdAt").value(toIsoLocalDateTime(now)));
    }

    @DisplayName("단축 URL을 임의로 만료시키며, 단축 URL 통계 정보를 삭제한다.")
    @Test
    void expireShortenLink() throws Exception {
        ExpireShortenLinkRequest request =
                new ExpireShortenLinkRequest(EXAMPLE_TODAY_EXPIRED.shortenPath, EXAMPLE_TODAY_EXPIRED.expireKey);

        mockMvc.perform(
                        post("/api/v1/expire")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk());

        verify(linkCommand, times(1)).expireLink(any(ExpireShortenLinkRequest.class));
    }

    private static String toIsoLocalDateTime(LocalDateTime time) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(time);
    }
}