package kr.lnsc.api.link.service;


import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.exception.LinkNotFoundException;
import kr.lnsc.api.link.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LinkQuery {

    private final LinkRepository linkRepository;

    public Link getLink(String shortenPath) {
        return linkRepository.findByShortenPath(shortenPath)
                .orElseThrow(LinkNotFoundException::new);
    }
}
