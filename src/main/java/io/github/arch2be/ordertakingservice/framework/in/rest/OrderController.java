package io.github.arch2be.ordertakingservice.framework.in.rest;


import io.github.arch2be.ordertakingservice.application.domain.model.Order;
import io.github.arch2be.ordertakingservice.application.domain.model.exception.DomainException;
import io.github.arch2be.ordertakingservice.application.ports.out.OnNewOrderUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v1/order")
class OrderController {
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);
    private final OnNewOrderUseCase onNewOrderUseCase;
    private final OrderMapper orderMapper;

    OrderController(OnNewOrderUseCase orderPublisher, final OrderMapper orderMapper) {
        this.onNewOrderUseCase = orderPublisher;
        this.orderMapper = orderMapper;
    }

    @PostMapping
    @PreAuthorize("hasRole('ORDER_SELLER')")
    ResponseEntity<UUID> createOrder(@RequestBody @Validated OrderRequest orderRequest) {
        final Order order = orderMapper.toDomain(orderRequest);
        onNewOrderUseCase.process(order);
        return ResponseEntity.ok(order.getUuid());
    }

    @ExceptionHandler({DomainException.class})
    ResponseEntity<String> handleException(DomainException exception) {
        log.info("Handle DomainException with message: " + exception.getMessage());
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
