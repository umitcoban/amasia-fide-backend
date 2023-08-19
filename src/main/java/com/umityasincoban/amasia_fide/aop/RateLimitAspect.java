package com.umityasincoban.amasia_fide.aop;

import com.umityasincoban.amasia_fide.annotation.RateLimit;
import com.umityasincoban.amasia_fide.exception.RateLimitExceededException;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {
    private final RedisTemplate<String, String> redisTemplate;

    @Around("@annotation(rateLimit)")
    public Object rateLimit(ProceedingJoinPoint joinPoint, RateLimit rateLimit) throws Throwable {
        String key = rateLimit.key();
        int maxAttempts = rateLimit.maxAttempts();
        int timeoutInSeconds = rateLimit.timeoutInSeconds();

        ValueOperations<String, String> operations = redisTemplate.opsForValue();
        String currentAttempts = operations.get(key);

        if (currentAttempts != null && Integer.parseInt(currentAttempts) >= maxAttempts) {
            throw new RateLimitExceededException("Maksimum deneme sayısına ulaşıldı. Lütfen daha sonra tekrar deneyin.");
        }

        int newAttempts = currentAttempts == null ? 1 : Integer.parseInt(currentAttempts) + 1;
        operations.set(key, String.valueOf(newAttempts), timeoutInSeconds, TimeUnit.SECONDS);

        return joinPoint.proceed();
    }
}
