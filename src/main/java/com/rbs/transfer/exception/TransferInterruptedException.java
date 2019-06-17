package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;

/**
 * Thrown if anything went wrong in transaction state
 * Takes {@link FunctionalErrorCode} in constructor to point specific point.
 */
public class TransferInterruptedException extends AccountException{
    public TransferInterruptedException() {
        super("Transfer process interrupted");
    }
    public TransferInterruptedException(FunctionalErrorCode errorCode) {
        super(errorCode.toString());
    }
    public TransferInterruptedException(String message){super(message);}
}
