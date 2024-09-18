package com.jupiterbank.account.config;

import com.jupiterbank.account.client.customer.CustomerClient;
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
    private final String customerApiBaseUrl;

    public AppConfig(@Value("${customer-api.base-url}") String customerApiBaseUrl) {
        this.customerApiBaseUrl = customerApiBaseUrl;
    }

    @Bean("customerApiRestClient")
    RestClient restClient() {
        return RestClient.create(this.customerApiBaseUrl);
    }

    @Bean
    CustomerClient accountClient(@Qualifier("customerApiRestClient") RestClient restClient) {
        var httpServiceProxyFactory = HttpServiceProxyFactory.builderFor(RestClientAdapter.create(restClient))
                .build();

        return httpServiceProxyFactory.createClient(CustomerClient.class);
    }
}
