package framework.out.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.arch2be.ordertaking.application.domain.model.Order;
import io.github.arch2be.ordertaking.application.ports.out.OrderPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
class RabbitMQOrderPublisher implements OrderPublisher {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQOrderPublisher.class);
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RabbitTemplate rabbitTemplate;

    @Value("${publisher.routing-key}")
    private String routingKey;

    @Value("${publisher.exchange-name}")
    private String exchange;

    RabbitMQOrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(final Order order) {
        log.info("New order ready for publish with id: " + order.getUuid());

        final String orderMappedToJson = mapToJson(order);

        if (!Objects.isNull(orderMappedToJson)) {
            rabbitTemplate.convertAndSend(exchange, routingKey, orderMappedToJson);
        }
    }

    private String mapToJson(Order order) {
        try {
            return objectMapper.writeValueAsString(order);
        } catch (JsonProcessingException e) {
            log.error("Problem during convert order to json.", e);
            return null;
        }
    }
}
