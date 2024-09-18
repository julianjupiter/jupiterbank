package com.jupiterbank.account.config;

import io.github.resilience4j.retry.RetryRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

/**
 * @author Julian Jupiter
 */
@Configuration
public class RetryConfig {
    private static final Logger log = LoggerFactory.getLogger(RetryConfig.class);

    public RetryConfig(RetryRegistry retryRegistry) {
        retryRegistry.getAllRetries()
                .forEach(retry -> retry
                        .getEventPublisher()
                        .onRetry(event -> log.info("{}", event))
                );
    }
}
