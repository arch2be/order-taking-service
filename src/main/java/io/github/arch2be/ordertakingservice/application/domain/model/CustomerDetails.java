package application.domain.model;

import io.github.arch2be.ordertaking.application.domain.model.exception.CustomerDetailsDomainException;

import java.time.LocalDateTime;

public record CustomerDetails(String clientName, String clientSurname, String installationAddress,
                              LocalDateTime preferredInstallationDate, String timeSlotDetails) {
    public CustomerDetails {
        validate();
    }

    public void validate() {
        if (preferredInstallationDate.isBefore(LocalDateTime.now())) {
            throw new CustomerDetailsDomainException("PreferredInstallationDate must be set as feature date");
        }
    }
}
