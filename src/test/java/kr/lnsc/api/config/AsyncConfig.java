package kr.lnsc.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;

import java.util.concurrent.Executor;

@TestConfiguration
public class AsyncConfig {

    @Bean
    public Executor executor() {
        return new SyncTaskExecutor();
    }
}
