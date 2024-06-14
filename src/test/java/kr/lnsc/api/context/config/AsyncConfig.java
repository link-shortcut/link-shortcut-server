package kr.lnsc.api.context.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.core.task.SyncTaskExecutor;
import org.springframework.scheduling.annotation.EnableAsync;

import java.util.concurrent.Executor;

@TestConfiguration
@EnableAsync
public class AsyncConfig {

    @Bean
    public Executor taskExecutor() {
        return new SyncTaskExecutor();
    }
}
