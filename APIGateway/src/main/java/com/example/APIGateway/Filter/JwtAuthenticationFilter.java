package com.example.APIGateway.Filter;


import com.example.APIGateway.Util.JwtUtil;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;
import java.util.List;

@Component
public class JwtAuthenticationFilter implements GatewayFilter {
    private final JwtUtil jwtUtil;

    public JwtAuthenticationFilter(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        List<String> openApiEndpoints = List.of(
                "/v1/auth/login",
                "/v1/auth/register",
                "/eureka"
        );
        boolean isSecured = openApiEndpoints.stream().noneMatch(uri -> request.getURI().getPath().contains(uri));
        if (isSecured) {
            if (authMissing(request))
                return onError(exchange);

            String authHeader = request.getHeaders().getFirst("Authorization");

            if (!authHeader.startsWith("Bearer "))
                return onError(exchange);

            String token = authHeader.substring(7);

            try {
                jwtUtil.validateToken(token);
            } catch (Exception e) {
                return onError(exchange);
            }
        }

        return chain.filter(exchange);
    }

    private boolean authMissing(ServerHttpRequest request) {
        return request.getHeaders().getFirst("Authorization") == null;
    }

    private Mono<Void> onError(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }
}
