package kr.lnsc.api.system.link.application;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.exception.LinkNotFoundException;
import kr.lnsc.api.system.link.domain.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LinkQuery {
    private final LinkRepository linkRepository;

    @Cacheable(value = "link", key = "#shortenPath")
    public Link getLink(String shortenPath) {
        return linkRepository.findLink(shortenPath)
                .orElseThrow(LinkNotFoundException::new);
    }
}
