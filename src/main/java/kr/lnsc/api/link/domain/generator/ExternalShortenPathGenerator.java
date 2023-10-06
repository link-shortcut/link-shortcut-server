package kr.lnsc.api.link.domain.generator;

import org.springframework.web.client.RestTemplate;

public class ExternalShortenPathGenerator implements ShortenPathGenerator {
    private final String url;
    private final RestTemplate restTemplate;

    public ExternalShortenPathGenerator(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public String generate() {
        return restTemplate.getForObject(url, String.class);
    }
}
