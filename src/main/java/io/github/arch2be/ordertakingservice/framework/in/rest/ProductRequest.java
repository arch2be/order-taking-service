package io.github.arch2be.ordertakingservice.framework.in.rest;


import org.springframework.lang.NonNull;

record ProductRequest(
        @NonNull String productType,
        @NonNull String details) {
}
