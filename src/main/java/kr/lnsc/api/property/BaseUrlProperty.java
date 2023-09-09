package kr.lnsc.api.property;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class BaseUrlProperty {
    private static String baseUrl;

    public BaseUrlProperty(@Value("${BASE_URL}") String baseUrl) {
        this.baseUrl = baseUrl;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
