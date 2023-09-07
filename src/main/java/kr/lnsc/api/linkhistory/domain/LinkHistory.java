package kr.lnsc.api.linkhistory.domain;

import jakarta.persistence.*;
import kr.lnsc.api.link.domain.Link;
import kr.lnsc.api.model.TimeBaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@AttributeOverride(name = "createdAt", column = @Column(name = "accessedAt", updatable = false, columnDefinition = "datetime(6)"))
public class LinkHistory extends TimeBaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "link_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Link link;

    private LinkHistory(Long id, Link link) {
        this.id = id;
        this.link = link;
    }

    public static LinkHistory from(Link link) {
        return new LinkHistory(null, link);
    }
}
