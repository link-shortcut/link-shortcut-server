package kr.lnsc.api.system.link.application;

import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.link.domain.generator.ShortenPathGenerator;
import kr.lnsc.api.system.link.exception.CanNotFoundUniquePathException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueLinkCreator {
    private static final int ATTEMPT_LIMIT = 3;

    private final LinkRepository linkRepository;
    private final ShortenPathGenerator shortenPathGenerator;

    public String create() {
        int attempt = 0;
        while (attempt < ATTEMPT_LIMIT) {
            String generatedShortenPath = shortenPathGenerator.generate();
            if (!linkRepository.existsByShortenPath(generatedShortenPath)) {
                return generatedShortenPath;
            }
            attempt++;
        }
        throw new CanNotFoundUniquePathException();
    }
}
