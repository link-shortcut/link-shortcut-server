package kr.lnsc.api.fixture;

import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.link.domain.Url;

import java.time.LocalDate;

public enum LinkFixture {
    EXAMPLE_TODAY_EXPIRED(new Url("http://www.example.com"), "aaaaaaa", "12345678", LocalDate.now()),
    EXAMPLE_NEXT_DAY_EXPIRED(new Url("http://www.example.com"), "bbbbbbb", "11111111", LocalDate.now().plusDays(1));

    public final Url originalUrl;
    public final String shortenPath;
    public final String expireKey;
    public final LocalDate expireDate;

    LinkFixture(Url originalUrl, String shortenPath, String expireKey, LocalDate expireDate) {
        this.originalUrl = originalUrl;
        this.shortenPath = shortenPath;
        this.expireKey = expireKey;
        this.expireDate = expireDate;
    }

    public Link toLink() {
        return Link.createLink(originalUrl, shortenPath, expireKey, expireDate);
    }
}
