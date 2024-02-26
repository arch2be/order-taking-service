package io.github.arch2be.ordertakingservice.application.ports.out;

import io.github.arch2be.ordertakingservice.application.domain.model.Order;

@FunctionalInterface
public interface OrderPublisher {

    void publish(Order order);
}
