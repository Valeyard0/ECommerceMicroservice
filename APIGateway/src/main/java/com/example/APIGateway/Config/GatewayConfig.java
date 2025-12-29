package com.example.APIGateway.Config;

import com.example.APIGateway.Filter.JwtAuthenticationFilter;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Configuration
public class GatewayConfig {
    private final JwtAuthenticationFilter filter;

    public GatewayConfig(JwtAuthenticationFilter filter) {
        this.filter = filter;
    }
    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return builder.routes()

                // PRODUCT-SERVICE
                .route("product-service", r -> r
                        .path("/api/v1/products/**", "/api/v1/categories/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://product-service"))

                // USER-SERVICE
                .route("user-service", r -> r
                        .path("/api/v1/users/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://user-service"))

                // ORDER-SERVICE (orders + cart)
                .route("order-service", r -> r
                        .path("/api/v1/order/**", "/api/v1/cart/**")
                        .filters(f -> f.rewritePath("/api/v1/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://order-service"))

                // EUREKA UI
                .route("eureka-server", r -> r
                        .path("/eureka/main")
                        .filters(f -> f.rewritePath("eureka/main", "/"))
                        .uri("http://localhost:8761"))

                .route("eureka-server-static", r -> r
                        .path("/eureka/**")
                        .uri("http://localhost:8761"))

                // PRODUCT-SERVICE
                .route("auth-service", r -> r
                        .path("/v1/auth/**")
                        .filters(f -> f.rewritePath("v1/(?<segment>.*)", "/api/v1/${segment}"))
                        .uri("lb://auth-service"))

                .build();
    }
}
