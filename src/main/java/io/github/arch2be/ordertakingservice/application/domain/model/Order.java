package io.github.arch2be.ordertakingservice.application.domain.model;

import io.github.arch2be.ordertakingservice.application.domain.model.exception.OrderDomainException;

import java.util.Objects;
import java.util.Set;
import java.util.UUID;

public class Order {
    private final UUID uuid;
    private final CustomerDetails customerDetails;
    private final Set<Product> productToInstall;

    public Order(final CustomerDetails customerDetails, final Set<Product> productToInstall) {
        this.uuid = UUID.randomUUID();
        this.customerDetails = customerDetails;
        this.productToInstall = productToInstall;
        validate();
    }

    public UUID getUuid() {
        return uuid;
    }

    public CustomerDetails getCustomerDetails() {
        return customerDetails;
    }

    public Set<Product> getProductToInstall() {
        return productToInstall;
    }

    public void validate() {
        if (Objects.isNull(productToInstall) || productToInstall.isEmpty()) {
            throw new OrderDomainException("ProductToInstall must be greater than 0");
        }
    }
}
