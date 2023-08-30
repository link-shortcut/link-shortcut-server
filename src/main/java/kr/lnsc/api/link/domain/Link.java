package kr.lnsc.api.link.domain;

import jakarta.persistence.*;
import kr.lnsc.api.model.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Link extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "original_url"))
    private Url originalUrl;

    @Column(name = "shorten_Path", unique = true)
    private String shortenPath;

    @Column(name = "expired_key")
    private String expiredKey;

    @Column(name = "expired_at", columnDefinition = "datetime(6)")
    private LocalDateTime expiredAt;

    private Link(Long id, Url originalUrl, String shortenPath, String expiredKey, LocalDateTime expiredAt) {
        this.id = id;
        this.originalUrl = originalUrl;
        this.shortenPath = shortenPath;
        this.expiredKey = expiredKey;
        this.expiredAt = expiredAt;
    }

    public static Link createLink(Url originalUrl, String shortenPath, String expiredKey, LocalDateTime expiredAt) {
        return new Link(null, originalUrl, shortenPath, expiredKey, expiredAt);
    }
}
