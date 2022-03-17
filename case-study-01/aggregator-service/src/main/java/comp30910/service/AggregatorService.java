package comp30910.service;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;
import comp30910.model.Cinema;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class AggregatorService {
    private final DiscoveryService discoveryService;
    private final RestTemplate restTemplate;

    @Value("${api_gateway_host}")
    private String apiGatewayHost;

    public List<Cinema> cinemaList(HttpMethod httpMethod, String endpoint) {
        TypeLiteral<Cinema> type = new TypeLiteral<>() {};
        return aggregate(httpMethod, endpoint, type, null);
    }

    private <T> List<T> aggregate(
            HttpMethod httpMethod, String endpoint, TypeLiteral<T> type, Object request) {
        List<T> results = new ArrayList<>();
        for (String serviceUrl : discoveryService.getCinemaUrlPrefixes()) {
            String url = apiGatewayHost + serviceUrl + endpoint;
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
