package com.rbs.transfer.exception.handler;

import com.rbs.transfer.domain.ErrorResponse;
import com.rbs.transfer.exception.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Responsible with business exception handling, only handle business/functional exceptions.
 *
 * Extends {@link BaseExceptionHandler}
 */
@ControllerAdvice
public class CustomExceptionHandler extends BaseExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(CustomExceptionHandler.class);

    @ExceptionHandler(AccountNotFoundException.class)
    protected ResponseEntity handleAccountNotFoundException(AccountNotFoundException ex) {
        LOG.error("Account not found, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalAmountException.class)
    protected ResponseEntity handleIllegalAmountException(IllegalAmountException ex) {
        LOG.error("Wrong amount, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InsufficientFundsException.class)
    protected ResponseEntity handleInsufficientFundsException(InsufficientFundsException ex) {
        LOG.error("Insufficient funds, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferInterruptedException.class)
    protected ResponseEntity handleTransferInterruptedException(TransferInterruptedException ex) {
        LOG.error("Transfer failed, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(TransferRollbackException.class)
    protected ResponseEntity handleTransferRollbackException(TransferRollbackException ex) {
        LOG.error("Transfer rollback failed, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AccountException.class)
    protected ResponseEntity handleAccountException(AccountException ex) {
        LOG.error("Progress failed, {}", ex);
        final ErrorResponse response = new ErrorResponse(false, ex.getMessage());
        return new ResponseEntity(response, HttpStatus.BAD_REQUEST);
    }
}