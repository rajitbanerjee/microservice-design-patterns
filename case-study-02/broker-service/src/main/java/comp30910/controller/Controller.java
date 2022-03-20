package comp30910.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.utils.SerializationUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import comp30910.model.Cinema;
import comp30910.model.RequestMessage;
import comp30910.service.BrokerService;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {
    private final BrokerService brokerService;

    @GetMapping("/movie/list")
    public List<Cinema> cinemaList() throws InterruptedException {
        String endpoint = "/movie/list";
        List<Message> responses = brokerService.processRequest(new RequestMessage(endpoint, null));
        return responses.stream()
                .map(r -> (Cinema) SerializationUtils.deserialize(r.getBody()))
                .collect(Collectors.toList());
    }
}
