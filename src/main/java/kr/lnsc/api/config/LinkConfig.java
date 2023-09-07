package kr.lnsc.api.config;

import kr.lnsc.api.link.domain.generator.ExpireKeyGenerator;
import kr.lnsc.api.link.domain.generator.RandomShortenPathGenerator;
import kr.lnsc.api.link.domain.generator.ShortenPathGenerator;
import kr.lnsc.api.link.domain.generator.UuidExpireKeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkConfig {

    @Bean
    public ShortenPathGenerator shortenPathGenerator() {
        return new RandomShortenPathGenerator();
    }

    @Bean
    public ExpireKeyGenerator expireKeyGenerator() {
        return new UuidExpireKeyGenerator();
    }
}
