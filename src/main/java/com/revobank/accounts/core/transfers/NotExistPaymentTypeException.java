package com.revobank.accounts.core.transfers;

public class NotExistPaymentTypeException extends RuntimeException {
    public NotExistPaymentTypeException(String message) {
        super(message);
    }
}
