package kr.lnsc.api.link.service;

import kr.lnsc.api.config.JpaConfig;
import kr.lnsc.api.link.repository.LinkRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JpaConfig.class)
public class ServiceTest {

    @Autowired
    private LinkRepository linkRepository;

    @AfterEach
    void clear() {
        linkRepository.deleteAll();
    }
}
