package kr.lnsc.api.ratelimit.interceptor;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import kr.lnsc.api.ratelimit.RedisRateLimiter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.servlet.HandlerInterceptor;

@RequiredArgsConstructor
public class RedisRateLimitInterceptor implements HandlerInterceptor {

    private final RedisRateLimiter rateLimiter;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String host = request.getRemoteHost();
        rateLimiter.checkAvailable(host);
        return true;
    }
}
