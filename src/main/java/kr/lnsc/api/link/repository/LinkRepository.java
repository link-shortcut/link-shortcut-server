package kr.lnsc.api.link.repository;

import kr.lnsc.api.link.domain.Link;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LinkRepository extends JpaRepository<Link, Long> {

    Optional<Link> findByShortenPath(String shortenPath);
}
