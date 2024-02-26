package io.github.arch2be.ordertakingservice.application.domain;

import io.github.arch2be.ordertakingservice.application.domain.model.Order;
import io.github.arch2be.ordertakingservice.application.ports.out.OrderPublisher;

public class OnNewOrderUseCase {
    private final OrderPublisher orderPublisher;

    public OnNewOrderUseCase(final OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    public void process(Order order) {
        orderPublisher.publish(order);
    }
}
