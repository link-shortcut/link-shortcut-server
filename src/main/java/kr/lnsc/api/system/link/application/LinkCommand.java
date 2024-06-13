package kr.lnsc.api.system.link.application;

import kr.lnsc.api.system.link.domain.Link;
import kr.lnsc.api.system.link.domain.LinkRepository;
import kr.lnsc.api.system.link.domain.Url;
import kr.lnsc.api.system.link.domain.generator.ExpireKeyGenerator;
import kr.lnsc.api.system.link.exception.InvalidExpireKeyException;
import kr.lnsc.api.system.link.presentation.request.CreateShortenLinkRequest;
import kr.lnsc.api.system.link.presentation.request.ExpireShortenLinkRequest;
import kr.lnsc.api.system.linkhistory.application.LinkHistoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Service
@RequiredArgsConstructor
public class LinkCommand {
    private final LinkRepository linkRepository;
    private final ExpireKeyGenerator expireKeyGenerator;
    private final UniqueLinkCreator uniqueLinkCreator;

    private final LinkQuery linkQuery;
    private final LinkHistoryCommand linkHistoryCommand;

    public Link createLink(CreateShortenLinkRequest request) {
        String shortenPath = uniqueLinkCreator.create();
        String expireKey = expireKeyGenerator.generate();

        Link createdLink = Link.createLink(
                new Url(request.getOriginalUrl()),
                shortenPath,
                expireKey,
                request.getExpireDate()
        );
        return linkRepository.save(createdLink);
    }

    @CacheEvict(value = "link", key = "#request.shortenPath")
    public void expireLink(ExpireShortenLinkRequest request) {
        Link findLink = linkQuery.getLink(request.getShortenPath());

        if (!findLink.isSameExpireKey(request.getExpireKey())) {
            throw new InvalidExpireKeyException();
        }
        linkHistoryCommand.removeAllLinkHistory(findLink);
        linkRepository.deleteById(findLink.getId());
    }

    public Link accessLink(String shortenPath) {
        Link findLink = linkQuery.getLink(shortenPath);
        linkHistoryCommand.createHistoryAsync(findLink);
        return findLink;
    }
}
