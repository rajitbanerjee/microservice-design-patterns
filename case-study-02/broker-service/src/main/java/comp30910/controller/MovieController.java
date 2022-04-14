package comp30910.controller;

import comp30910.model.Cinema;
import comp30910.model.RequestMessage;
import comp30910.service.BrokerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/movie")
public class MovieController {
    private final BrokerService brokerService;

    @Value("${amqp.request.routingKey}")
    private String requestRoutingKey;

    @GetMapping("/list")
    public String movieList() {
        String endpoint = "/movie/list";
        String routingKey = requestRoutingKey;
        String correlationId =
                brokerService.sendRequest(new RequestMessage(endpoint, null), routingKey);
        return correlationId;
    }

    @GetMapping("/list/{correlationId}")
    public List<Cinema> movieListResponse(@PathVariable String correlationId) {
        List<Message> responses = brokerService.fetchResponseFromCache(correlationId);
        return responses.stream()
                .map(r -> (Cinema) SerializationUtils.deserialize(r.getBody()))
                .collect(Collectors.toList());
    }
}
