package kr.lnsc.api.link.controller;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.response.CreateShortenLinkResponse;
import kr.lnsc.api.link.service.LinkCommand;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class LinkController {
    private final LinkCommand linkCommand;
    private final LinkHistoryCommand linkHistoryCommand;

    @PostMapping("api/v1/create")
    public CreateShortenLinkResponse createShortenLink(@Validated @RequestBody CreateShortenLinkRequest request) {
        Link newLink = linkCommand.createLink(request);
        return CreateShortenLinkResponse.from(newLink);
    }

    @GetMapping("/{shortenPath}")
    public ResponseEntity<?> redirectToOriginalUrl(@PathVariable String shortenPath) {
        Link findLink = linkHistoryCommand.accessLink(shortenPath);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(findLink.originalUri());
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
