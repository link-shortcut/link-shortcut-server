package kr.lnsc.api.link.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.lnsc.api.link.domain.Url;
import kr.lnsc.api.link.validator.ExpireTime;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
public class CreateShortenLinkRequest {

    @Schema(description = "단축할 원본 URL")
    @NotNull(message = "단축할 URL 주소를 입력해주세요.")
    private Url originalUrl;

    @Schema(description = "단축 URL 만료일자")
    @ExpireTime
    private LocalDate expireDate;

    public CreateShortenLinkRequest(Url originalUrl, LocalDate expireDate) {
        this.originalUrl = originalUrl;
        this.expireDate = expireDate;
    }

    public String getOriginalUrl() {
        return originalUrl.getValue();
    }

    public LocalDate getExpireDate() {
        return expireDate;
    }
}
