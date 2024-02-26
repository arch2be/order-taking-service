package io.github.arch2be.ordertakingservice.framework.in.rest;


import io.github.arch2be.ordertakingservice.application.domain.model.CustomerDetails;
import io.github.arch2be.ordertakingservice.application.domain.model.Order;
import io.github.arch2be.ordertakingservice.application.domain.model.Product;
import io.github.arch2be.ordertakingservice.application.domain.model.ProductType;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.stream.Collectors;

@Component
class OrderMapper {

    Order toDomain(OrderRequest request) {
        return new Order(toCustomerDetails(request), toProductsInstall(request));
    }

    private Set<Product> toProductsInstall(final OrderRequest request) {
        return request.productToInstall().stream()
                .map(this::toProduct)
                .collect(Collectors.toSet());
    }

    private Product toProduct(final ProductRequest productRequest) {
        return new Product(
                ProductType.valueOf(productRequest.productType()),
                productRequest.details());
    }

    private CustomerDetails toCustomerDetails(final OrderRequest request) {
        return new CustomerDetails(
                request.clientName(),
                request.clientSurname(),
                request.installationAddress(),
                request.preferredInstallationDate(),
                request.timeSlotDetails());
    }
}
