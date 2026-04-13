package com.dogmeeting.apigatewayservice.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

//@Configuration -> yml파일로 전환
public class FilterConfig {
    Environment environment;

    public FilterConfig(Environment environment) {
        this.environment = environment;
    }

    //@Bean -> yml파일로 전환
    public RouteLocator routeLocator(RouteLocatorBuilder builder) {
        return builder.routes()
                .route(r -> r.path("/first-service/**")
                        .filters(f -> f.addRequestHeader("f-request", "1st-request-header-by-java")
                                .addRequestHeader("f-response", "1st-response-header-by-java"))
                        .uri("http://localhost:8081"))
                .route(r -> r.path("/second-service/**")
                        .filters(s -> s.addRequestHeader("s-request", "2nd-request-header-by-java")
                                .addRequestHeader("s-response", "2nd-response-header-by-java"))
                        .uri("http://localhost:8082"))
                .build();
    }
}
