package comp30910.controller;

import comp30910.model.Cinema;
import comp30910.model.Movie;
import comp30910.model.RequestMessage;
import comp30910.model.Reservation;
import comp30910.service.MovieService;
import comp30910.service.ReservationService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Controller {
    private static final String SERVICE_PREFIX = "cinema-";
    private static final String SERVICE_SUFFIX = "-service";

    private final MovieService movieService;
    private final ReservationService reservationsService;

    private final RabbitTemplate rabbitTemplate;
    private final TopicExchange exchange;

    @Qualifier("responseQueue")
    private final Queue responseQueue;

    @Value("${spring.application.name}")
    private String serviceName;

    @Value("${amqp.response.routingKey}")
    private String responseRoutingKey;

    @RabbitListener(queues = "#{requestQueue.name}")
    public void receiveRequestSendResponse(RequestMessage request, Message message) {
        String correlationId = message.getMessageProperties().getCorrelationId();

        MessagePostProcessor messagePostProcessor =
                responseMessage -> {
                    MessageProperties messageProperties = responseMessage.getMessageProperties();
                    messageProperties.setReplyTo(responseQueue.getName());
                    messageProperties.setCorrelationId(correlationId);
                    return responseMessage;
                };

        Object response = null;
        switch (request.getEndpoint()) {
                // Movie
            case "/movie/list":
                response = list();
                break;

                // Reservation
            case "/reservation/make":
                Reservation reservation = (Reservation) request.getBody();
                response = make(reservation);
                break;
        }
        rabbitTemplate.convertAndSend(
                exchange.getName(), responseRoutingKey, response, messagePostProcessor);
    }

    public Cinema list() {
        String id = Integer.valueOf(serviceName.hashCode()).toString();
        String cinemaName =
                serviceName.replaceFirst(SERVICE_PREFIX, "").replaceFirst(SERVICE_SUFFIX, "");
        List<Movie> movies = movieService.findAll();
        return new Cinema(id, cinemaName, movies);
    }

    public Reservation make(Reservation reservation) {
        return reservationsService.makeReservation(reservation);
    }
}
