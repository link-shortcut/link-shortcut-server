package kr.lnsc.api.link.domain.response;

import java.util.List;

public record ZookeeperExternalShortenPathResponse(
        List<String> paths
) {
}
