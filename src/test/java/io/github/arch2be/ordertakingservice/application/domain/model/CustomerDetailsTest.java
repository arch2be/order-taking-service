package io.github.arch2be.ordertakingservice.application.domain.model;


import io.github.arch2be.ordertakingservice.application.domain.model.exception.CustomerDetailsDomainException;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CustomerDetailsTest {

    @Test
    void shouldThrowDomainException() {
        var thrown = assertThrows(
                CustomerDetailsDomainException.class,
                this::testCustomerDetailsWithWrongDate,
                "Expected testCustomerDetailsWithWrongDate() to throw, but it didn't"
        );

        assertTrue(thrown.getMessage().contains("PreferredInstallationDate must be set as feature date"));
    }

    private void testCustomerDetailsWithWrongDate() {
        new CustomerDetails(
                "client-name",
                "client-surname",
                "test-address",
                LocalDateTime.now().minusDays(1),
                "timeslot");
    }
}