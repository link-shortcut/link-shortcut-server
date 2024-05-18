package kr.lnsc.api.system.link.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    @Query("SELECT l FROM Link l WHERE l.shortenPath = :shortenPath AND l.expiredAt > now()")
    Optional<Link> findLink(String shortenPath);

    boolean existsByShortenPath(String shortenPath);
}
