package kr.lnsc.api.link.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import kr.lnsc.api.link.exception.InvalidUrlException;
import kr.lnsc.api.property.BaseUrlProperty;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Url implements Serializable {
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
                !PATTERN.matcher(value).matches() ||
                PermittedHost.HOSTS.contains(extractHost(value))) {
            throw new InvalidUrlException();
        }
    }

    public static List<String> permittedHosts(String... urlSpecs) {
        List<String> permittedHosts = new ArrayList<>();

        for (String spec : urlSpecs) {
            String host = extractHost(spec);
            if (host.startsWith("www.")) {
                permittedHosts.add(host.split("www.")[1]);
            }
            permittedHosts.add(host);
        }

        return Collections.unmodifiableList(permittedHosts);
    }

    private static String extractHost(String urlSpec) {
        try {
            return new URL(urlSpec).getHost();
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
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

    private static class PermittedHost {
        private static final String[] URL_SPECS_FOR_PERMISSION = {BaseUrlProperty.getBaseUrl()};
        private static final List<String> HOSTS = permittedHosts(URL_SPECS_FOR_PERMISSION);
    }
}
