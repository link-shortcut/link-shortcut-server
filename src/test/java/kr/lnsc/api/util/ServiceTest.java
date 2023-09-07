package kr.lnsc.api.util;

import kr.lnsc.api.config.JpaConfig;
import kr.lnsc.api.link.repository.LinkRepository;
import kr.lnsc.api.linkhistory.repository.LinkHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JpaConfig.class)
public class ServiceTest {

    @Autowired
    protected LinkRepository linkRepository;

    @Autowired
    protected LinkHistoryRepository linkHistoryRepository;

    @AfterEach
    void clear() {
        linkHistoryRepository.deleteAll();
        linkRepository.deleteAll();
    }
}
