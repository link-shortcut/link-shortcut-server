package kr.lnsc.api.system.link.application;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.link.exception.LinkNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LinkQuery {
    private final LinkRepository linkRepository;

    @Cacheable(value = "link",
            key = "#shortenPath",
            unless = "#result.expiredAt.isBefore(T(java.time.LocalDateTime).now().plusSeconds(1800))")
    public Link getLink(String shortenPath) {
        return linkRepository.findLink(shortenPath)
                .orElseThrow(LinkNotFoundException::new);
    }
}
