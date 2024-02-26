package framework.in.rest;

import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.Set;

record OrderRequest(
        @NonNull String clientName,
        @NonNull String clientSurname,
        @NonNull String installationAddress,
        @NonNull LocalDateTime preferredInstallationDate,
        @NonNull String timeSlotDetails,
        @NonNull Set<ProductRequest> productToInstall) {
}
