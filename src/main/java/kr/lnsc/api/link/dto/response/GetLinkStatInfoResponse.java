package kr.lnsc.api.link.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import kr.lnsc.api.link.domain.Link;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class GetLinkStatInfoResponse {

    @Schema(description = "원본 URL")
    private String originalUrl;

    @Schema(description = "오늘 접속 횟수")
    private long dailyAccessCount;

    @Schema(description = "누적 접속 횟수")
    private long totalAccessCount;

    @Schema(description = "단축 URL 만료일자")
    private LocalDateTime expiredAt;

    @Schema(description = "단축 URL 생성일자")
    private LocalDateTime createdAt;

    private GetLinkStatInfoResponse(String originalUrl, long dailyAccessCount, long totalAccessCount, LocalDateTime expiredAt, LocalDateTime createdAt) {
        this.originalUrl = originalUrl;
        this.dailyAccessCount = dailyAccessCount;
        this.totalAccessCount = totalAccessCount;
        this.expiredAt = expiredAt;
        this.createdAt = createdAt;
    }

    public static GetLinkStatInfoResponse of(Link link, long dailyAccessCount, long totalAccessCount) {
        return new GetLinkStatInfoResponse(
                link.getOriginalUrl().getValue(),
                dailyAccessCount,
                totalAccessCount,
                link.getExpiredAt(),
                link.getCreatedAt()
        );
    }
}
