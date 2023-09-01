package kr.lnsc.api.link.controller;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.response.CreateShortenLinkResponse;
import kr.lnsc.api.link.service.LinkCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class LinkController {
    private final LinkCommand linkCommand;

    @PostMapping("api/v1/create")
    public CreateShortenLinkResponse createShortenLink(@Validated @RequestBody CreateShortenLinkRequest request) {
        Link newLink = linkCommand.createLink(request);
        return CreateShortenLinkResponse.from(newLink);
    }
}
