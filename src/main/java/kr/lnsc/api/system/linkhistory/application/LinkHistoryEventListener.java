package kr.lnsc.api.system.linkhistory.application;

import jakarta.persistence.EntityManager;
import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.linkhistory.domain.event.AppendLinkHistoryEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class LinkHistoryEventListener {
    private final EntityManager entityManager;
    private final LinkHistoryCommand linkHistoryCommand;

    @Async("taskExecutor")
    @EventListener(AppendLinkHistoryEvent.class)
    public void appendLinkHistory(AppendLinkHistoryEvent event) {
        try {
            Link linkProxy = entityManager.getReference(Link.class, event.getLinkId());
            linkHistoryCommand.createHistory(linkProxy);
        } catch (Exception e) {
            log.error("Async task saving link history was failed.", e);
        }
    }
}
