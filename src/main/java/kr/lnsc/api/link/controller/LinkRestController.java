package kr.lnsc.api.link.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.dto.request.CreateShortenLinkRequest;
import kr.lnsc.api.link.dto.request.ExpireShortenLinkRequest;
import kr.lnsc.api.link.dto.response.CreateShortenLinkResponse;
import kr.lnsc.api.link.dto.response.GetLinkStatInfoResponse;
import kr.lnsc.api.link.service.LinkCommand;
import kr.lnsc.api.link.service.LinkQuery;
import kr.lnsc.api.linkhistory.service.LinkHistoryQuery;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Tag(name = "link REST API", description = "링크 REST API")
@RestController
@RequiredArgsConstructor
public class LinkRestController {
    private final LinkCommand linkCommand;
    private final LinkQuery linkQuery;
    private final LinkHistoryQuery linkHistoryQuery;

    @Operation(summary = "단축 URL 생성", description = "URL과 만료일자를 입력해 단축 URL을 생성합니다.")
    @PostMapping("/api/link/create")
    public CreateShortenLinkResponse createShortenLink(@Validated @RequestBody CreateShortenLinkRequest request) {
        Link newLink = linkCommand.createLink(request);
        return CreateShortenLinkResponse.from(newLink);
    }

    @Operation(summary = "단축 URL 임의 만료", description = "해당 단축 URL을 임의로 만료합니다.")
    @PostMapping("/api/link/expire")
    public void expireShortenLink(@Validated @RequestBody ExpireShortenLinkRequest request) {
        linkCommand.expireLink(request);
    }

    @Operation(summary = "단축 URL 통계 정보 조회", description = "해당 단축 URL의 통계 정보를 조회합니다.")
    @GetMapping("/api/link/{shortenPath}")
    public GetLinkStatInfoResponse getLinkStatisticalInformation(
            @Parameter(description = "단축 URL Path") @PathVariable String shortenPath
    ) {
        Link findLink = linkQuery.getLink(shortenPath);
        long dailyAccessCount = linkHistoryQuery.getAccessCount(findLink, LocalDate.now());
        long totalAccessCount = linkHistoryQuery.getTotalAccessCount(findLink);
        return GetLinkStatInfoResponse.of(findLink, dailyAccessCount, totalAccessCount);
    }
}
