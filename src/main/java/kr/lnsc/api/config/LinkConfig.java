package kr.lnsc.api.config;

import kr.lnsc.api.link.domain.generator.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration
public class LinkConfig {

    @Bean
    @ConditionalOnProperty(prefix = "path-generator", name = "type", havingValue = "external")
    public RestTemplate restTemplate(RestTemplateBuilder restTemplateBuilder) {
        return restTemplateBuilder
                .setConnectTimeout(Duration.ofMillis(700))
                .setReadTimeout(Duration.ofMillis(700))
                .build();
    }

    @Bean
    @ConditionalOnProperty(prefix = "path-generator", name = "type", havingValue = "external")
    public ShortenPathGenerator externalShortenPathGenerator(@Value("${path-generator.url}") String url, RestTemplate restTemplate) {
        return new ExternalShortenPathGenerator(url, restTemplate);
    }

    @Bean
    @ConditionalOnMissingBean
    public ShortenPathGenerator shortenPathGenerator() {
        return new RandomShortenPathGenerator();
    }

    @Bean
    public ExpireKeyGenerator expireKeyGenerator() {
        return new UuidExpireKeyGenerator();
    }
}
