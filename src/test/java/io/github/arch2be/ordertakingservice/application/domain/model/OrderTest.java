package io.github.arch2be.ordertakingservice.application.domain.model;

import io.github.arch2be.ordertakingservice.application.domain.model.exception.OrderDomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {
    @Test
    void shouldThrowDomainException() {
        var thrown = assertThrows(
                OrderDomainException.class,
                this::testOrderWithEmptyProductList,
                "Expected testOrderWithEmptyProductList() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("ProductToInstall must be greater than 0"));
    }

    private void testOrderWithEmptyProductList() {
        new Order(testCustomerDetails(), Collections.emptySet());
    }

    private CustomerDetails testCustomerDetails() {
        return new CustomerDetails(
                "client-name",
                "client-surname",
                "test-address",
                LocalDateTime.now().plusDays(3),
                "timeslot");
    }
}