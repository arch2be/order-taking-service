package io.github.arch2be.ordertakingservice.framework.out.rabbitmq;


import io.github.arch2be.ordertakingservice.application.domain.model.Order;
import io.github.arch2be.ordertakingservice.application.ports.out.OnNewOrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
class RabbitMQOrderPublisher implements OnNewOrderUseCase {
    private static final Logger log = LoggerFactory.getLogger(RabbitMQOrderPublisher.class);
    private final RabbitTemplate rabbitTemplate;

    @Value("${publisher.routing-key}")
    private String routingKey;

    @Value("${publisher.exchange-name}")
    private String exchange;

    RabbitMQOrderPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void process(final Order order) {
        log.info("New order ready for publish with id: " + order.getUuid());
        rabbitTemplate.convertAndSend(exchange, routingKey, order);
    }
}
