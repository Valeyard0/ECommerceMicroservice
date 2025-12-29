package com.example.AuthService.Client.UserClientService;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class UserServiceClientConfig {
    @Bean
    public UserServiceClient productServiceInterface(RestClient.Builder restClientBuilder){
        RestClient restClient = restClientBuilder.baseUrl("http://user-service").build();
        RestClientAdapter adapter = RestClientAdapter.create(restClient);
        HttpServiceProxyFactory factory = HttpServiceProxyFactory.builderFor(adapter).build();
        UserServiceClient userServiceClient = factory.createClient(UserServiceClient.class);
        return userServiceClient;
    }
}
