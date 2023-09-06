package kr.lnsc.api.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.linkhistory.service.LinkHistoryCommand;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.view.RedirectView;

@Tag(name = "link API", description = "링크 API")
@Controller
@RequiredArgsConstructor
public class LinkController {
    private final LinkHistoryCommand linkHistoryCommand;

    @Operation(summary = "단축 URL 접속", description = "단축 URL로 접속하면 원래 URL로 리다이렉트됩니다.")
    @GetMapping("/{shortenPath}")
    public RedirectView redirectToOriginalUrl(
            @Parameter(description = "단축 URL Path") @PathVariable String shortenPath
    ) {
        Link findLink = linkHistoryCommand.accessLink(shortenPath);
        return new RedirectView(findLink.getOriginalUrl().getValue());
    }
}
