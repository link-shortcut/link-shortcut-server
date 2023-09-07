package kr.lnsc.api.util;

import com.google.common.base.CaseFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.metamodel.EntityType;
import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.domain.LinkHistory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

import static kr.lnsc.api.fixture.LinkFixture.*;
import static kr.lnsc.api.fixture.LinkHistoryFixture.ACCESS_LAST_WEEK;
import static kr.lnsc.api.fixture.LinkHistoryFixture.ACCESS_TODAY;

@TestComponent
public class DatabaseInitializer implements InitializingBean {

    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    private TestLinkHistoryRepository testLinkHistoryRepository;

    private List<String> tableNames;

    @Override
    public void afterPropertiesSet() {
        this.tableNames = entityManager.getMetamodel()
                .getEntities().stream()
                .map(EntityType::getJavaType)
                .filter(entityType -> entityType.isAnnotationPresent(Entity.class))
                .map(Class::getSimpleName)
                .map(this::toSnakeCase)
                .toList();
    }

    @Transactional
    public void clear() {
        entityManager.flush();
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = FALSE").executeUpdate();

        for (String tableName : tableNames) {
            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
            entityManager.createNativeQuery("ALTER TABLE " + tableName + " AUTO_INCREMENT = 1")
                    .executeUpdate();
        }
        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = TRUE").executeUpdate();
    }

    @Transactional
    public void setUp() {
        Link alreadyExpiredLink = EXAMPLE_ALREADY_EXPIRED.toLink();
        Link todayExpiredLink = EXAMPLE_TODAY_EXPIRED.toLink();
        Link nextDayExpiredLink = EXAMPLE_NEXT_DAY_EXPIRED.toLink();
        addEntity(alreadyExpiredLink, todayExpiredLink, nextDayExpiredLink);

        LinkHistory alreadyExpiredLinkLastWeekHistory = ACCESS_LAST_WEEK.toLinkHistory(alreadyExpiredLink);
        LinkHistory todayExpiredLinkLastWeekHistory = ACCESS_LAST_WEEK.toLinkHistory(todayExpiredLink);
        LinkHistory nextdayExpiredLinkTodayHistory = ACCESS_TODAY.toLinkHistory(nextDayExpiredLink);
        addEntity(alreadyExpiredLinkLastWeekHistory,
                todayExpiredLinkLastWeekHistory,
                nextdayExpiredLinkTodayHistory);

        testLinkHistoryRepository.updateAccessedAt(alreadyExpiredLinkLastWeekHistory, ACCESS_LAST_WEEK.accessedAt);
        testLinkHistoryRepository.updateAccessedAt(todayExpiredLinkLastWeekHistory, ACCESS_LAST_WEEK.accessedAt);
        testLinkHistoryRepository.updateAccessedAt(nextdayExpiredLinkTodayHistory, ACCESS_TODAY.accessedAt);
    }

    private <T> void addEntity(T... entities) {
        Arrays.asList(entities).forEach(entityManager::persist);
        entityManager.flush();
    }

    private String toSnakeCase(String camelCaseString) {
        return CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, camelCaseString);
    }
}
