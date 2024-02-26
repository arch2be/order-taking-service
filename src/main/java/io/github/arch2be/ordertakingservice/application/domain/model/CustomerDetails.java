package io.github.arch2be.ordertakingservice.application.domain.model;

import io.github.arch2be.ordertakingservice.application.domain.model.exception.CustomerDetailsDomainException;

import java.time.LocalDateTime;
import java.util.Objects;

public class CustomerDetails {
    private final String clientName;
    private final String clientSurname;
    private final String installationAddress;
    private final LocalDateTime preferredInstallationDate;
    private final String timeSlotDetails;

    public CustomerDetails(final String clientName,
                           final String clientSurname,
                           final String installationAddress,
                           final LocalDateTime preferredInstallationDate,
                           final String timeSlotDetails) {
        this.clientName = clientName;
        this.clientSurname = clientSurname;
        this.installationAddress = installationAddress;
        this.preferredInstallationDate = preferredInstallationDate;
        this.timeSlotDetails = timeSlotDetails;

        validate();
    }


    public void validate() {
        if (Objects.isNull(preferredInstallationDate) || preferredInstallationDate.isBefore(LocalDateTime.now())) {
            throw new CustomerDetailsDomainException("PreferredInstallationDate must be set as feature date");
        }
    }

    public String getClientName() {
        return clientName;
    }

    public String getClientSurname() {
        return clientSurname;
    }

    public String getInstallationAddress() {
        return installationAddress;
    }

    public LocalDateTime getPreferredInstallationDate() {
        return preferredInstallationDate;
    }

    public String getTimeSlotDetails() {
        return timeSlotDetails;
    }
}
