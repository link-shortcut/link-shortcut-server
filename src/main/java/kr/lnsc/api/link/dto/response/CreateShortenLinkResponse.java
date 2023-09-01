package kr.lnsc.api.link.dto.response;

import kr.lnsc.api.link.domain.Link;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateShortenLinkResponse {
    private String shortenUrl;
    private LocalDateTime expiredAt;

    public CreateShortenLinkResponse(String shortenUrl, LocalDateTime expiredAt) {
        this.shortenUrl = shortenUrl;
        this.expiredAt = expiredAt;
    }

    public static CreateShortenLinkResponse from(Link link) {
        return new CreateShortenLinkResponse(link.shortenUrl(), link.getExpiredAt());
    }
}
