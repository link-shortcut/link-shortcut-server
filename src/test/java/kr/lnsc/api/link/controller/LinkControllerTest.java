package kr.lnsc.api.link.controller;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.domain.Url;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.service.LinkCommand;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class LinkControllerTest extends ControllerTest {

    @MockBean
    private LinkCommand linkCommand;

    @DisplayName("입력한 값을 바탕으로 단축 URL을 생성한다.")
    @Test
    void createShortenLink() throws Exception {
        final Url ORIGINAL_URL = new Url("http://www.example.com");
        final String SHORTEN_PATH = "aaaaaaa";
        final String EXPIRE_KEY = "12345678";
        LocalDate TODAY = LocalDate.now();

        CreateShortenLinkRequest request = new CreateShortenLinkRequest(ORIGINAL_URL, TODAY);
        given(linkCommand.createLink(any(CreateShortenLinkRequest.class)))
                .willReturn(Link.createLink(ORIGINAL_URL, SHORTEN_PATH, EXPIRE_KEY, TODAY));

        LocalDateTime expectedExpiredAt = LocalDateTime.of(TODAY.plusDays(1), LocalTime.MIN);
        mockMvc.perform(
                        post("/api/v1/create")
                                .contentType(APPLICATION_JSON)
                                .content(objectMapper.writeValueAsBytes(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.shortenUrl").value("https://www.test.kr/" + SHORTEN_PATH))
                .andExpect(jsonPath("$.expiredAt").value(toIsoLocalDateTime(expectedExpiredAt)))
                .andExpect(jsonPath("$.expireKey").value(EXPIRE_KEY));
    }

    private static String toIsoLocalDateTime(LocalDateTime time) {
        return DateTimeFormatter.ISO_LOCAL_DATE_TIME.format(time);
    }
}