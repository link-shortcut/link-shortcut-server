package kr.lnsc.api.link.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import kr.lnsc.api.link.validator.ExpireTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class CreateShortenLinkRequest {

    @Schema(description = "단축할 원본 URL")
    @NotNull(message = "단축할 URL 주소를 입력해주세요.")
    @URL(message = "잘못된 URL 형식입니다. 확인 후 다시 입력해주세요.")
    private String originalUrl;

    @Schema(description = "단축 URL 만료일자")
    @ExpireTime
    private LocalDate expireDate;

    public CreateShortenLinkRequest(String originalUrl, LocalDate expireDate) {
        this.originalUrl = originalUrl;
        this.expireDate = expireDate;
    }
}
