package kr.lnsc.api.config;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.cache.CacheManager;
import org.springframework.cache.support.SimpleCacheManager;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class CacheConfig {

    @Bean
    public CacheManager cacheManager() {
        return new SimpleCacheManager();
    }
}
