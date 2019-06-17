package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;

/**
 * Thrown after transaction failed and rollback progress is not successfully completed.
 * Takes {@link FunctionalErrorCode} in constructor to point actual reason caused failure.
 */
public class TransferRollbackException extends Throwable {
    public TransferRollbackException() {
        super("Could not rollback transfer");
    }

    public TransferRollbackException(FunctionalErrorCode errorCode) {
        super(errorCode.toString());
    }
    public TransferRollbackException(String message) {
        super(message);
    }
}
