package kr.lnsc.api.link.dto.response;

import kr.lnsc.api.link.domain.Link;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateShortenLinkResponse {
    private String shortenUrl;
    private LocalDateTime expiredAt;
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
