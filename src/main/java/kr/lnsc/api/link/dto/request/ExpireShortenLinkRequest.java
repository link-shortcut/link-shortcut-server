package kr.lnsc.api.link.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ExpireShortenLinkRequest {

    @Schema(description = "단축 URL의 Path")
    @NotBlank(message = "단축 URL의 Path를 입력해주세요.")
    private String shortenPath;

    @Schema(description = "단축 URL 만료키")
    @NotBlank(message = "만료키를 입력해주세요.")
    private String expireKey;

    public ExpireShortenLinkRequest(String shortenPath, String expireKey) {
        this.shortenPath = shortenPath;
        this.expireKey = expireKey;
    }
}
