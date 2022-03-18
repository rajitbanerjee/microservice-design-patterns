package comp30910.util;

import com.jsoniter.JsonIterator;
import com.jsoniter.spi.TypeLiteral;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.web.client.RestTemplate;

@RequiredArgsConstructor
public class RestClient {
    private final RestTemplate restTemplate;

    public <T> T getForObject(String url, TypeLiteral<T> type) {
        String response = restTemplate.getForObject(url, String.class);
        return JsonIterator.deserialize(response, type);
    }

    public <T, E> T postForObject(String url, E body, TypeLiteral<T> type) {
        HttpEntity<E> request = new HttpEntity<E>(body);
        String response = restTemplate.postForObject(url, request, String.class);
        return JsonIterator.deserialize(response, type);
    }
}
