package kr.lnsc.api.linkhistory.repository;

import kr.lnsc.api.linkhistory.domain.LinkHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LinkHistoryRepository extends JpaRepository<LinkHistory, Long> {
}
