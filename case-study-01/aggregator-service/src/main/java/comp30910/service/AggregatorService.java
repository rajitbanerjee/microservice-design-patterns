package comp30910.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;
import comp30910.model.Movie;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AggregatorService {
    private final DiscoveryService discoveryService;
    private final RestTemplate restTemplate;
    private static final String GATEWAY_HOST = "lb://API-GATEWAY-SERVICE";

    public List<Movie> movieList(HttpMethod httpMethod, String endpoint) {
        TypeLiteral<List<Movie>> type = new TypeLiteral<>() {};
        List<List<Movie>> results = aggregate(endpoint, httpMethod, type, null);
        return results.stream().flatMap(List::stream).collect(Collectors.toList());
    }

    private <T> List<T> aggregate(
            String endpoint, HttpMethod httpMethod, TypeLiteral<T> type, Object request) {
        List<T> results = new ArrayList<>();
        for (String serviceUrl : discoveryService.getCinemaUrlPrefixes()) {
            String url = GATEWAY_HOST + serviceUrl + endpoint;
            String response = "";
            if (httpMethod.matches("GET")) {
                response = restTemplate.getForObject(url, String.class);
            } else if (httpMethod.matches("POST")) {
                response = restTemplate.postForObject(url, request, String.class);
            }
            results.add(JsonIterator.deserialize(response, type));
        }
        return results;
    }
}
