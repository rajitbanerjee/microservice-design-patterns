package comp30910.service;

import comp30910.model.RequestMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BrokerService {
    private final RabbitTemplate rabbitTemplate;
    private final Map<String, List<Message>> cache;
    private final TopicExchange exchange;

    public List<Message> processRequest(RequestMessage requestMessage, String routingKey)
            throws InterruptedException {
        String correlationId = UUID.randomUUID().toString();
        sendRequest(requestMessage, correlationId, routingKey);
        Thread.sleep(500); // Intentional delay
        return cache.get(correlationId);
    }

    private void sendRequest(
            RequestMessage requestMessage, String correlationId, String routingKey) {
        MessagePostProcessor messagePostProcessor =
                message -> {
                    MessageProperties messageProperties = message.getMessageProperties();
                    messageProperties.setCorrelationId(correlationId);
                    return message;
                };
        rabbitTemplate.setMessageConverter(new SerializerMessageConverter());
        rabbitTemplate.convertAndSend(
                exchange.getName(), routingKey, requestMessage, messagePostProcessor);
    }

    @RabbitListener(queues = "#{responseQueue.name}")
    private void receiveResponse(Message message) {
        String correlationId = message.getMessageProperties().getCorrelationId();
        List<Message> messages = cache.getOrDefault(correlationId, new ArrayList<>());
        messages.add(message);
        cache.put(correlationId, messages);
    }
}
