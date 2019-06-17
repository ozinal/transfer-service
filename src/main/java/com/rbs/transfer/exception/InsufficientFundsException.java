package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;

/**
 * Thrown when account doesn't has sufficient funds available
 * Takes {@link FunctionalErrorCode} in constructor to point specific point.
 */
public class InsufficientFundsException extends AccountException{
    public InsufficientFundsException() {
        super("Insufficient funds");
    }
    public InsufficientFundsException(FunctionalErrorCode errorCode) {
        super(errorCode.toString());
    }
    public InsufficientFundsException(String message){super(message);}
}
