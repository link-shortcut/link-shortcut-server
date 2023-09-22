package kr.lnsc.api.config;

import com.google.common.net.HttpHeaders;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    private static final String ALLOWED_METHODS = "GET,HEAD,POST,DELETE,TRACE,OPTIONS,PATCH,PUT";

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(
                        "http://lnsc.me",
                        "http://www.lnsc.me",
                        "https://lnsc.me",
                        "https://www.lnsc.me")
                .allowedMethods(ALLOWED_METHODS.split(","))
                .allowedHeaders("*")
                .allowCredentials(true)
                .exposedHeaders(HttpHeaders.LOCATION);
    }
}
