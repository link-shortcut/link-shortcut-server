package kr.lnsc.api.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.lnsc.api.link.exception.InvalidUrlException;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url {
    public static final int URL_MAX_LENGTH = 2048;
    private static final Pattern PATTERN = Pattern.compile("https?:\\/\\/(www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{2,256}\\.[a-z]{2,6}\\b([-a-zA-Z0-9@:%_\\+.~#()?&//=]*)");

    @Column(name = "url", length = URL_MAX_LENGTH)
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Url url = (Url) o;
        return Objects.equals(getValue(), url.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getValue());
    }
}
