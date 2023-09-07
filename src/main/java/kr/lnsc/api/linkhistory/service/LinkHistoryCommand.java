package kr.lnsc.api.linkhistory.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.service.LinkQuery;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import kr.lnsc.api.linkhistory.repository.LinkHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LinkHistoryCommand {
    private final LinkHistoryRepository linkHistoryRepository;
    private final LinkQuery linkQuery;

    public LinkHistory createHistory(Link link) {
        LinkHistory newLinkHistory = LinkHistory.from(link);
        return linkHistoryRepository.save(newLinkHistory);
    }

    public Link accessLink(String shortenPath) {
        Link findLink = linkQuery.getLink(shortenPath);
        this.createHistory(findLink);
        return findLink;
    }

    public void removeAllLinkHistory(Link link) {
        linkHistoryRepository.deleteAllByLink(link);
    }
}
