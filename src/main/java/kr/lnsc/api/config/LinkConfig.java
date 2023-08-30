package kr.lnsc.api.config;

import kr.lnsc.api.link.domain.generator.RandomShortenPathGenerator;
import kr.lnsc.api.link.domain.generator.ShortenPathGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LinkConfig {

    @Bean
    public ShortenPathGenerator shortenPathGenerator() {
        return new RandomShortenPathGenerator();
    }
}
