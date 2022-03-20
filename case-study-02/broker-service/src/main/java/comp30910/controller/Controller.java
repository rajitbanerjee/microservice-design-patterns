package comp30910.controller;

import comp30910.model.RequestMessage;
import comp30910.service.BrokerService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class Controller {
    private final BrokerService brokerService;

    @GetMapping("/movie/list")
    public List<String> cinemaList() throws InterruptedException {
        String endpoint = "/movie/list";
        List<Message> responses = brokerService.processRequest(new RequestMessage(endpoint, null));
        return responses.stream().map(r -> new String(r.getBody())).collect(Collectors.toList());
    }
}
