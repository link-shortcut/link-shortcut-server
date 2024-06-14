package kr.lnsc.api.system.linkhistory.domain;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.presentation.response.LinkAccessCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LinkHistoryRepository extends JpaRepository<LinkHistory, Long> {

    @Query("SELECT new kr.lnsc.api.system.link.presentation.response.LinkAccessCountDto(date(lh.createdAt) as date_only, count(1)) " +
            "FROM LinkHistory lh " +
            "WHERE lh.link = :link " +
            "GROUP BY date_only " +
            "ORDER BY date_only")
    List<LinkAccessCountDto> findAccessCountByDate(Link link);

    void deleteAllByLink(Link link);
}
