package cl.duoc.ms_gateway;

import org.springframework.cloud.gateway.server.mvc.filter.BeforeFilterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.GatewayRouterFunctions;
import org.springframework.cloud.gateway.server.mvc.handler.HandlerFunctions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.function.RequestPredicates;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.ServerResponse;

@Configuration
public class GatewayConfig {

    @Bean
    public RouterFunction<ServerResponse> routes() {
        return GatewayRouterFunctions.route("ms-auth")
                .route(RequestPredicates.path("/auth/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9011"))
                .build()
            .and(GatewayRouterFunctions.route("ms-clients")
                .route(RequestPredicates.path("/api/v1/clients/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9001"))
                .build())
            .and(GatewayRouterFunctions.route("ms-sales")
                .route(RequestPredicates.path("/api/v1/sales/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9002"))
                .build())
            .and(GatewayRouterFunctions.route("ms-employees")
                .route(RequestPredicates.path("/api/v1/employees/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9003"))
                .build())
            .and(GatewayRouterFunctions.route("ms-vehicles")
                .route(RequestPredicates.path("/api/v1/vehicles/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9004"))
                .build())
            .and(GatewayRouterFunctions.route("ms-inventory")
                .route(RequestPredicates.path("/api/v1/inventory/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9005"))
                .build())
            .and(GatewayRouterFunctions.route("ms-test-drive")
                .route(RequestPredicates.path("/api/v1/test-drives/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9006"))
                .build())
            .and(GatewayRouterFunctions.route("ms-suppliers")
                .route(RequestPredicates.path("/api/v1/suppliers/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9007"))
                .build())
            .and(GatewayRouterFunctions.route("ms-delivery")
                .route(RequestPredicates.path("/api/v1/deliveries/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9008"))
                .build())
            .and(GatewayRouterFunctions.route("ms-branches")
                .route(RequestPredicates.path("/api/v1/branches/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9009"))
                .build())
            .and(GatewayRouterFunctions.route("ms-insurances")
                .route(RequestPredicates.path("/api/v1/insurances/**"), HandlerFunctions.http())
                .before(BeforeFilterFunctions.uri("http://localhost:9010"))
                .build());
    }
}
