package kr.lnsc.api.util;

import kr.lnsc.api.context.config.JpaConfig;
import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.linkhistory.domain.LinkHistoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(JpaConfig.class)
public class ServiceTest {

    @Autowired
    protected LinkRepository linkRepository;

    @Autowired
    protected LinkHistoryRepository linkHistoryRepository;

    @Autowired
    private CacheManager cacheManager;

    @AfterEach
    void clear() {
        linkHistoryRepository.deleteAll();
        linkRepository.deleteAll();
        clearCache();
    }

    private void clearCache() {
        cacheManager.getCacheNames().stream()
                .map(cacheManager::getCache)
                .forEach(Cache::clear);
    }
}
