package kr.lnsc.api.util;

import jakarta.persistence.EntityManager;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@TestComponent
public class TestLinkHistoryRepository {

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void updateAccessedAt(LinkHistory savedLinkHistory, LocalDateTime accessedAt) {
        entityManager.createQuery("update LinkHistory lh set lh.createdAt = :accessedAt where lh = :savedLinkHistory")
                .setParameter("accessedAt", accessedAt)
                .setParameter("savedLinkHistory", savedLinkHistory)
                .executeUpdate();
    }
}
