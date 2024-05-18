package kr.lnsc.api.system.linkhistory.application;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.linkhistory.domain.LinkHistory;
import kr.lnsc.api.system.linkhistory.domain.LinkHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
@Slf4j
public class LinkHistoryCommand {
    private final LinkHistoryRepository linkHistoryRepository;

    public LinkHistory createHistory(Link link) {
        LinkHistory newLinkHistory = LinkHistory.from(link);
        return linkHistoryRepository.save(newLinkHistory);
    }

    @Async
    public void createHistoryAsync(Link link) {
        try {
            this.createHistory(link);
        } catch (IllegalArgumentException | OptimisticLockingFailureException e) {
            log.error("Async task saving link history was failed.", e);
        }
    }

    public void removeAllLinkHistory(Link link) {
        linkHistoryRepository.deleteAllByLink(link);
    }
}
