package application.domain;

import io.github.arch2be.ordertaking.application.domain.model.Order;
import io.github.arch2be.ordertaking.application.ports.out.OrderPublisher;

public class OnNewOrderUseCase {
    private final OrderPublisher orderPublisher;

    public OnNewOrderUseCase(final OrderPublisher orderPublisher) {
        this.orderPublisher = orderPublisher;
    }

    public void process(Order order) {
        orderPublisher.publish(order);
    }
}
