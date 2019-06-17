package com.rbs.transfer.exception.handler;

import com.rbs.transfer.domain.ErrorResponse;
import com.rbs.transfer.exception.*;
import com.rbs.transfer.utils.FunctionalErrorCode;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class CustomExceptionHandlerTest {

    @InjectMocks
    private CustomExceptionHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleAccountNotFoundException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleAccountNotFoundException(new AccountNotFoundException(FunctionalErrorCode.COULD_NOT_TRANSFER));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.COULD_NOT_TRANSFER.toString(), actual.getBody().getErrorMessage());
    }

    @Test
    public void handleIllegalAmountException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleIllegalAmountException(new IllegalAmountException(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL.toString(), actual.getBody().getErrorMessage());
    }

    @Test
    public void handleInsufficientFundsException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleInsufficientFundsException(new InsufficientFundsException(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE.toString(), actual.getBody().getErrorMessage());
    }

    @Test
    public void handleTransferInterruptedException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleTransferInterruptedException(new TransferInterruptedException(FunctionalErrorCode.COULD_NOT_TRANSFER));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.COULD_NOT_TRANSFER.toString(), actual.getBody().getErrorMessage());
    }

    @Test
    public void handleTransferRollbackException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleTransferRollbackException(new TransferRollbackException(FunctionalErrorCode.INTERNAL_ERROR));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.INTERNAL_ERROR.toString(), actual.getBody().getErrorMessage());
    }

    @Test
    public void handleAccountException_should_return_failed_response() {
        ResponseEntity<ErrorResponse> actual = this.handler.handleAccountException(new AccountException(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND));

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
        assertFalse(actual.getBody().isStatus());
        assertEquals(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND.toString(), actual.getBody().getErrorMessage());
    }
}