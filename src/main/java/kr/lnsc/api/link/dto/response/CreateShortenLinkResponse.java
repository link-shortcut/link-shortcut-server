package kr.lnsc.api.link.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.lnsc.api.link.domain.Link;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateShortenLinkResponse {

    @Schema(description = "단축된 URL")
    private String shortenUrl;

    @Schema(description = "단축 URL 만료일자")
    private LocalDateTime expiredAt;

    @Schema(description = "단축 URL 만료키")
    private String expireKey;

    public CreateShortenLinkResponse(String shortenUrl, LocalDateTime expiredAt, String expireKey) {
        this.shortenUrl = shortenUrl;
        this.expiredAt = expiredAt;
        this.expireKey = expireKey;
    }

    public static CreateShortenLinkResponse from(Link link) {
        return new CreateShortenLinkResponse(link.shortenUrl(), link.getExpiredAt(), link.getExpireKey());
    }
}
