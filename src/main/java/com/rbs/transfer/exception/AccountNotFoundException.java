package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;

/**
 * Thrown when account doesn't exists.
 * Takes {@link FunctionalErrorCode} in custom constructor
 */
public class AccountNotFoundException extends AccountException {

    private FunctionalErrorCode functionalErrorCode;

    public AccountNotFoundException() {
        super("Account not found.");
    }

    public AccountNotFoundException(FunctionalErrorCode errorCode) {
        super(errorCode.toString());
        this.functionalErrorCode = errorCode;

    }

    public AccountNotFoundException(String message) {
        super(message);
    }
}