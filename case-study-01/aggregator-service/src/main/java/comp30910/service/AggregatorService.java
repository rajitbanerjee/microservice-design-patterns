package comp30910.service;

import com.jsoniter.spi.TypeLiteral;
import comp30910.model.Cinema;
import comp30910.util.RestClient;
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

    private <T, E> List<T> aggregate(
            HttpMethod httpMethod, String endpoint, TypeLiteral<T> type, E body) {
        List<T> results = new ArrayList<>();
        RestClient restClient = new RestClient(restTemplate);

        for (String serviceUrl : discoveryService.getCinemaUrlPrefixes()) {
            String url = apiGatewayHost + serviceUrl + endpoint;
            if (httpMethod.matches("GET")) {
                results.add(restClient.getForObject(url, type));
            } else if (httpMethod.matches("POST")) {
                results.add(restClient.postForObject(url, body, type));
            }
        }
        return results;
    }
}
