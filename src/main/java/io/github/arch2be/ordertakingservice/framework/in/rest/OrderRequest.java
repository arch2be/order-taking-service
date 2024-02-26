package io.github.arch2be.ordertakingservice.framework.in.rest;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

record OrderRequest(
        @NonNull String clientName,
        @NonNull String clientSurname,
        @NonNull String installationAddress,
        @NonNull @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime preferredInstallationDate,
        @NonNull String timeSlotDetails,
        @NonNull Set<ProductRequest> productToInstall) {
}
