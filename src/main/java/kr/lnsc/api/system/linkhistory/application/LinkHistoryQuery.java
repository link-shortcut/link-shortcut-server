package kr.lnsc.api.system.linkhistory.application;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.presentation.response.LinkAccessCountDto;
import kr.lnsc.api.system.linkhistory.domain.LinkHistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class LinkHistoryQuery {
    private final LinkHistoryRepository linkHistoryRepository;

    public List<LinkAccessCountDto> getAccessCountByDate(Link link) {
        return linkHistoryRepository.findAccessCountByDate(link);
    }

    public long getAccessCount(Link link, LocalDate date) {
        return getAccessCountByDate(link).stream()
                .filter(ac -> ac.isEqualDate(date))
                .map(LinkAccessCountDto::getCount)
                .findFirst()
                .orElse(0l);
    }

    public long getTotalAccessCount(Link link) {
        return getAccessCountByDate(link).stream()
                .map(LinkAccessCountDto::getCount)
                .reduce(Long::sum)
                .orElse(0l);
    }
}
