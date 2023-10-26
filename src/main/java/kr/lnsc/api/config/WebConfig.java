package kr.lnsc.api.config;

import com.google.common.net.HttpHeaders;
import kr.lnsc.api.ratelimit.RedisRateLimiter;
import kr.lnsc.api.ratelimit.interceptor.RedisRateLimitInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class WebConfig implements WebMvcConfigurer {
    private static final String ALLOWED_METHODS = "GET,HEAD,POST,DELETE,TRACE,OPTIONS,PATCH,PUT";

    private final RedisRateLimiter redisRateLimiter;

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

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RedisRateLimitInterceptor(redisRateLimiter));
    }
}
