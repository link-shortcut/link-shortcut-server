package kr.lnsc.api.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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

@Tag(name = "link", description = "링크 API")
@RestController
@RequiredArgsConstructor
public class LinkController {
    private final LinkCommand linkCommand;
    private final LinkHistoryCommand linkHistoryCommand;

    @Operation(summary = "단축 URL 생성", description = "URL과 만료일자를 입력해 단축 URL을 생성합니다.")
    @PostMapping("api/v1/create")
    public CreateShortenLinkResponse createShortenLink(@Validated @RequestBody CreateShortenLinkRequest request) {
        Link newLink = linkCommand.createLink(request);
        return CreateShortenLinkResponse.from(newLink);
    }

    @Operation(summary = "단축 URL 접속", description = "단축 URL로 접속하면 원래 URL로 리다이렉트됩니다.")
    @GetMapping("/{shortenPath}")
    public ResponseEntity<?> redirectToOriginalUrl(
            @Parameter(description = "단축 URL Path") @PathVariable String shortenPath
    ) {
        Link findLink = linkHistoryCommand.accessLink(shortenPath);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(findLink.originalUri());
        return new ResponseEntity<>(headers, HttpStatus.MOVED_PERMANENTLY);
    }
}
