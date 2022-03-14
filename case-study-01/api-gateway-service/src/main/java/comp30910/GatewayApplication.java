package comp30910;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JCircuitBreakerFactory;
import org.springframework.cloud.circuitbreaker.resilience4j.Resilience4JConfigBuilder;
import org.springframework.cloud.client.circuitbreaker.Customizer;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder.Builder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.timelimiter.TimeLimiterConfig;

@EnableEurekaClient
@SpringBootApplication
public class GatewayApplication {
    @Autowired
    private EurekaClient discoveryClient;
    private static final String CINEMA_SERVICE_PREFIX = "cinema-";
    private static final String CINEMA_SERVICE_SUFFIX = "-service";
    private static final String ROUTE_SUFFIX = "-route";
    private static final String CIRCUIT_BREAKER_SUFFIX = "-cb";
    private static final String LOAD_BALANCER_PREFIX = "lb://";
    private static final String FALLBACK_PREFIX = "forward:/fallback/";

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

    @Bean
    public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
        return makeRoutes(builder.routes());
    }

    private RouteLocator makeRoutes(Builder builder) {
        Stream<Application> registered = discoveryClient.getApplications().getRegisteredApplications().stream();
        Stream<String> registeredLowerCase = registered.map(app -> app.getName().toLowerCase());
        List<String> cinemaServiceNames = registeredLowerCase
                .filter(appName -> appName.startsWith(CINEMA_SERVICE_PREFIX))
                .collect(Collectors.toList());

        String CINEMA_PATH = "/cinema/%s/**";
        for (String serviceName : cinemaServiceNames) {
            String name = serviceName.replaceFirst(CINEMA_SERVICE_PREFIX, "").replaceFirst(CINEMA_SERVICE_SUFFIX, "");
            builder = builder.route(name + ROUTE_SUFFIX, r -> r.path(String.format(CINEMA_PATH, name))
                    .filters(f -> f.circuitBreaker(c -> c.setName(serviceName + CIRCUIT_BREAKER_SUFFIX)
                            .setFallbackUri(FALLBACK_PREFIX + serviceName)))
                    .uri(LOAD_BALANCER_PREFIX + serviceName.toUpperCase()));
        }
        return builder.build();
    }

    @Bean
    public Customizer<Resilience4JCircuitBreakerFactory> defaultCustomizer() {
        return factory -> factory.configureDefault(id -> builder(id).build());
    }

    private Resilience4JConfigBuilder builder(String id) {
        return new Resilience4JConfigBuilder(id)
                .timeLimiterConfig(
                        TimeLimiterConfig.custom().timeoutDuration(Duration.ofSeconds(3)).build())
                .circuitBreakerConfig(CircuitBreakerConfig.ofDefaults());
    }
}
