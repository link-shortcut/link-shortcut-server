package kr.lnsc.api.link.controller;

import kr.lnsc.api.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;

import static kr.lnsc.api.fixture.LinkFixture.EXAMPLE_TODAY_EXPIRED;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LinkControllerTest extends ControllerTest {

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
}
