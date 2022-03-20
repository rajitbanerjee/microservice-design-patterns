package comp30910.controller;

import comp30910.model.Cinema;
import comp30910.model.RequestMessage;
import comp30910.model.Reservation;
import comp30910.service.BrokerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {
    private final BrokerService brokerService;

    @Value("${amqp.request.routingKey}")
    private String requestRoutingKey;

    @GetMapping("/movie/list")
    public List<Cinema> movieList() throws InterruptedException {
        String endpoint = "/movie/list";
        String routingKey = requestRoutingKey;
        List<Message> responses =
                brokerService.processRequest(new RequestMessage(endpoint, null), routingKey);
        return responses.stream()
                .map(r -> (Cinema) SerializationUtils.deserialize(r.getBody()))
                .collect(Collectors.toList());
    }

    @PostMapping("/cinema/{cinemaName}/reservation/make")
    public Reservation reservationMake(
            @PathVariable String cinemaName, @RequestBody Reservation reservation)
            throws InterruptedException {
        String endpoint = "/reservation/make";
        String routingKey = String.format("%s.%s", requestRoutingKey, cinemaName);
        List<Message> responses =
                brokerService.processRequest(new RequestMessage(endpoint, reservation), routingKey);
        return responses.stream()
                .map(r -> (Reservation) SerializationUtils.deserialize(r.getBody()))
                .findFirst()
                .get();
    }
}
