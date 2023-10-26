package kr.lnsc.api.ratelimit;

import kr.lnsc.api.ratelimit.exception.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.time.*;

@Component
@RequiredArgsConstructor
public class RedisRateLimiter {
    private static final String KEY_PREFIX = "rate-limit:";
    private static final Integer MAX_REQUESTS_FOR_SECOND = 10;

    private final RedisTemplate<String, String> redisTemplate;

    public void checkAvailable(String clientIP) {
        String rateLimitKey = getRateLimitKey(clientIP);
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();

        String rateLimitCount = valueOperations.get(rateLimitKey);
        if (rateLimitCount == null) {
            valueOperations.set(rateLimitKey, "1");
            redisTemplate.expire(rateLimitKey, Duration.ofSeconds(1));
            return;
        }

        int counter = Integer.valueOf(rateLimitCount);
        if (counter >= MAX_REQUESTS_FOR_SECOND) {
            throw new RateLimitExceededException();
        }

        valueOperations.increment(rateLimitKey);
    }

    private String getRateLimitKey(String clientIP) {
        return KEY_PREFIX + getTime() + ":" + clientIP;
    }

    private long getTime() {
        return Instant.now().getEpochSecond();
    }
}
