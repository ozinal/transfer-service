package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Base exception where the rest of the exception under exception package extends.
 */
@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class AccountException extends Exception {

    public AccountException(FunctionalErrorCode errorCode) {
        super(errorCode.toString());
    }
    public AccountException(String message) {
        super(message);
    }
}