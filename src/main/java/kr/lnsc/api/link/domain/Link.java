package kr.lnsc.api.link.domain;

import jakarta.persistence.*;
import kr.lnsc.api.model.TimeBaseEntity;
import kr.lnsc.api.property.BaseUrlProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.net.URI;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Link extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "original_url", length = Url.URL_MAX_LENGTH))
    private Url originalUrl;

    @Column(name = "shorten_path", unique = true, columnDefinition = "varbinary(10)")
    private String shortenPath;

    @Column(name = "expire_key")
    private String expireKey;

    @Column(name = "expired_at", columnDefinition = "datetime(6)")
    private LocalDateTime expiredAt;

    private Link(Long id, Url originalUrl, String shortenPath, String expireKey, LocalDateTime expiredAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortenPath = shortenPath;
        this.expireKey = expireKey;
        this.expiredAt = expiredAt;
    }

    public static Link createLink(Url originalUrl, String shortenPath, String expireKey, LocalDate expireDate) {
        return new Link(null, originalUrl, shortenPath, expireKey, toExpiredAt(expireDate));
    }

    public String shortenUrl() {
        return String.format("%s/%s", BaseUrlProperty.getBaseUrl(), this.shortenPath);
    }

    public URI originalUri() {
        return URI.create(originalUrl.getValue());
    }

    public boolean isSameExpireKey(String expireKey) {
        return this.expireKey.equals(expireKey);
    }

    private static LocalDateTime toExpiredAt(LocalDate expireDate) {
        return LocalDateTime.of(expireDate.plusDays(1), LocalTime.MIN);
    }
}
