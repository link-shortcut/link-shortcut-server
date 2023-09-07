package kr.lnsc.api.util;

import io.restassured.RestAssured;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({DatabaseInitializer.class, TestLinkHistoryRepository.class})
public class AcceptanceTest {

    @LocalServerPort
    int port;

    @Autowired
    private DatabaseInitializer databaseInitializer;

    @BeforeEach
    void setUp() {
        RestAssured.port = port;
        databaseInitializer.setUp();
    }

    @AfterEach
    void clear() {
        databaseInitializer.clear();
    }
}