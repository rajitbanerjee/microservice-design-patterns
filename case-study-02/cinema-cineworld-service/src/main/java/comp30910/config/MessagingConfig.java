package comp30910.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.amqp.support.converter.SerializerMessageConverter;
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

    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(amqpExchange);
    }

    @Bean
    @Qualifier("requestQueue")
    public Queue requestQueue() {
        return new Queue("requestQueue-" + serviceName);
    }

    @Bean
    @Qualifier("requestBinding")
    public Binding requestBinding(TopicExchange exchange, @Qualifier("requestQueue") Queue queue) {
        return BindingBuilder.bind(queue).to(exchange).with(requestRoutingKey);
    }

    @Bean
    @Qualifier("responseQueue")
    public Queue responseQueue() {
        return new Queue("responseQueue");
    }

    @Bean
    public MessageConverter messageConverter() {
        return new SerializerMessageConverter();
    }
}
