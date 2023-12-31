package kr.lnsc.api.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import kr.lnsc.api.link.controller.LinkController;
import kr.lnsc.api.link.controller.LinkRestController;
import kr.lnsc.api.link.service.LinkCommand;
import kr.lnsc.api.link.service.LinkQuery;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import kr.lnsc.api.linkhistory.service.LinkHistoryQuery;
import kr.lnsc.api.property.BaseUrlProperty;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@WebMvcTest(controllers = {LinkRestController.class, LinkController.class})
@Import(BaseUrlProperty.class)
public class ControllerTest {

    @MockBean
    protected LinkCommand linkCommand;

    @MockBean
    protected LinkQuery linkQuery;

    @MockBean
    protected LinkHistoryQuery linkHistoryQuery;

    @MockBean
    protected LinkHistoryCommand linkHistoryCommand;

    protected MockMvc mockMvc;

    protected ObjectMapper objectMapper;

    @BeforeEach
    void setUp(WebApplicationContext context) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .addFilter(new CharacterEncodingFilter("UTF-8", true))
                .alwaysDo(print())
                .build();

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
    }
}
