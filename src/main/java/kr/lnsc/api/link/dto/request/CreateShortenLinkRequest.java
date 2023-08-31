package kr.lnsc.api.link.dto.request;

import jakarta.validation.constraints.NotNull;
import kr.lnsc.api.link.domain.Url;
import kr.lnsc.api.link.validator.ExpireTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateShortenLinkRequest {

    @NotNull(message = "단축할 URL 주소를 입력해주세요.")
    private Url originalUrl;

    @ExpireTime
    private LocalDate expireDate;

    public CreateShortenLinkRequest(Url originalUrl, LocalDate expireDate) {
        this.originalUrl = originalUrl;
        this.expireDate = expireDate;
    }
}
