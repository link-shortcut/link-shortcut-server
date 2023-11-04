package kr.lnsc.api.link.domain.generator;

import kr.lnsc.api.link.domain.response.ZookeeperExternalShortenPathResponse;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayDeque;
import java.util.Queue;

public class ExternalShortenPathGenerator implements ShortenPathGenerator {
    private static final int FETCH_SHORTEN_PATH_SIZE = 30;

    private final String url;
    private final RestTemplate restTemplate;

    private static Queue<String> shortenPathCache = new ArrayDeque<>();

    public ExternalShortenPathGenerator(String url, RestTemplate restTemplate) {
        this.url = url;
        this.restTemplate = restTemplate;
    }

    @Override
    public String generate() {
        if (shortenPathCache.isEmpty()) {
            ZookeeperExternalShortenPathResponse shortenPaths = restTemplate.getForObject(requestUrl(), ZookeeperExternalShortenPathResponse.class);
            shortenPathCache = new ArrayDeque<>(shortenPaths.paths());
        }
        return shortenPathCache.poll();
    }

    private String requestUrl() {
        return url + "?count=" + FETCH_SHORTEN_PATH_SIZE;
    }
}