package kr.lnsc.api.linkhistory.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.dto.LinkAccessCountDto;
import kr.lnsc.api.linkhistory.repository.LinkHistoryRepository;
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
