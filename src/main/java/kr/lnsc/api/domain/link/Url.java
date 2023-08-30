package kr.lnsc.api.domain.link;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.lnsc.api.domain.link.exception.InvalidUrlException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    private static final int URL_MAX_LENGTH = 2048;
    private static final Pattern PATTERN = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#()?&//=]*)");

    @Column(length = URL_MAX_LENGTH)
    private String value;

    public Url(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (!StringUtils.hasText(value) ||
                value.length() > URL_MAX_LENGTH ||
                !PATTERN.matcher(value).matches()) {
            throw new InvalidUrlException();
        }
    }
}
