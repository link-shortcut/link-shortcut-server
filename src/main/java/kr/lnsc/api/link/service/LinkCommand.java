package kr.lnsc.api.link.service;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.domain.generator.ExpireKeyGenerator;
import kr.lnsc.api.link.domain.generator.ShortenPathGenerator;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.exception.CanNotFoundUniquePathException;
import kr.lnsc.api.link.repository.LinkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LinkCommand {
    private static final int ATTEMPT_LIMIT = 30;

    private final LinkRepository linkRepository;
    private final ShortenPathGenerator shortenPathGenerator;
    private final ExpireKeyGenerator expireKeyGenerator;

    public Link createLink(CreateShortenLinkRequest request) {
        String shortenPath = getUniqueShortenPath();
        String expireKey = expireKeyGenerator.generate();

        Link createdLink = Link.createLink(
                request.getOriginalUrl(),
                shortenPath,
                expireKey,
                request.getExpireDate()
        );
        return linkRepository.save(createdLink);
    }

    private String getUniqueShortenPath() {
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
