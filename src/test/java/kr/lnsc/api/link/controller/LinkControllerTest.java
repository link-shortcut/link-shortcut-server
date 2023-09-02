package kr.lnsc.api.link.controller;

import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.service.LinkCommand;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import kr.lnsc.api.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class LinkControllerTest extends ControllerTest {

    @MockBean
    private LinkCommand linkCommand;

    @MockBean
    private LinkHistoryCommand linkHistoryCommand;

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

    private static String toIsoLocalDateTime(LocalDateTime time) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(time);
    }
}