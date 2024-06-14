package kr.lnsc.api.system.linkhistory.domain.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class AppendLinkHistoryEvent {
    private final Long linkId;

    public static AppendLinkHistoryEvent from(Long linkId) {
        return new AppendLinkHistoryEvent(linkId);
    }
}
