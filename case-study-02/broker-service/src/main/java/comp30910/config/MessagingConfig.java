package comp30910.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MessagingConfig {
    @Value("${amqp.exchange}")
    private String amqpExchange;

    @Value("${amqp.request.routingKey}")
    private String requestRoutingKey;

    @Value("${amqp.response.routingKey}")
    private String responseRoutingKey;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(amqpExchange);
    }

    @Bean
    @Qualifier("responseQueue")
    public Queue responseQueue() {
        return new Queue("responseQueue");
    }

    @Bean
    @Qualifier("responseBinding")
    public Binding responseBinding(
            TopicExchange exchange, @Qualifier("responseQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(responseRoutingKey);
    }

    @Bean
    public MessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
