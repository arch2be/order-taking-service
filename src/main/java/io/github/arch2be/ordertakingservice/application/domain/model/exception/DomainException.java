package io.github.arch2be.ordertakingservice.application.domain.model.exception;

public class DomainException extends RuntimeException {

    public DomainException(String message) {
        super(message);
    }
}
