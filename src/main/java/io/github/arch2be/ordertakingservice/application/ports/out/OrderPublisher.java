package application.ports.out;

import io.github.arch2be.ordertaking.application.domain.model.Order;

@FunctionalInterface
public interface OrderPublisher {

    void publish(Order order);
}
