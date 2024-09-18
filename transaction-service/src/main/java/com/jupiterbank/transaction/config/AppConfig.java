package com.jupiterbank.transaction.config;

import com.jupiterbank.transaction.client.account.AccountClient;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

/**
 * @author Julian Jupiter
 */
@Configuration
@EnableRetry
public class AppConfig {
    private final String accountApiBaseUrl;

    public AppConfig(@Value("${account-api.base-url}") String accountApiBaseUrl) {
        this.accountApiBaseUrl = accountApiBaseUrl;
    }

    @Bean("accountApiRestClient")
    RestClient restClient() {
        return RestClient.create(this.accountApiBaseUrl);
    }

    @Bean
    AccountClient accountClient(@Qualifier("accountApiRestClient") RestClient restClient) {
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return httpServiceProxyFactory.createClient(AccountClient.class);
    }
}
