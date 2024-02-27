package io.github.arch2be.ordertakingservice.framework.in.rest;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.lifecycle.Startables;

import java.util.stream.Stream;

class TestContainersConfig {

    @Container
    public static final RabbitMQContainer rabbitMQContainer = new RabbitMQContainer("rabbitmq:3.8-management-alpine");

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry dynamicPropertyRegistry) {
        dynamicPropertyRegistry.add("spring.rabbitmq.host", rabbitMQContainer::getHost);
        dynamicPropertyRegistry.add("spring.rabbitmq.port", rabbitMQContainer::getAmqpPort);

        try {
            rabbitMQContainer.execInContainer("rabbitmqadmin", "declare", "exchange", "name=test-exchange", "type=direct");
            rabbitMQContainer.execInContainer("rabbitmqadmin", "declare", "queue", "name=test-queue");
            rabbitMQContainer.execInContainer("rabbitmqadmin", "declare", "binding", "source=test-exchange", "destination=test-queue", "routing_key=order");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    static  {
        Startables.deepStart(Stream.of(rabbitMQContainer)).join();
    }
}
