package com.rbs.util;

import com.rbs.transfer.exception.*;
import com.rbs.transfer.utils.FunctionalErrorCode;

public class Thrower {

    public void throw_AccountException_with_FunctionalErrorCode() throws AccountException {
        throw new AccountException(FunctionalErrorCode.COULD_NOT_TRANSFER);
    }

    public void throw_AccountException_with_error_message() throws AccountException {
        throw new AccountException("Something went wrong!");
    }

    public void throw_AccountNotFoundException() throws AccountNotFoundException {
        throw new AccountNotFoundException();
    }

    public void throw_AccountNotFoundException_with_FunctionalErrorCode() throws AccountNotFoundException {
        throw new AccountNotFoundException(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL);
    }

    public void throw_AccountNotFoundException_with_error_message() throws AccountNotFoundException {
        throw new AccountNotFoundException("Something went wrong!");
    }

    public void throw_IllegalAmountException() throws IllegalAmountException {
        throw new IllegalAmountException();
    }

    public void throw_IllegalAmountException_with_FunctionalErrorCode() throws IllegalAmountException {
        throw new IllegalAmountException(FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO);
    }

    public void throw_IllegalAmountException_with_error_message() throws IllegalAmountException {
        throw new IllegalAmountException("Something went wrong!");
    }

    public void throw_InsufficientFundsException() throws InsufficientFundsException {
        throw new InsufficientFundsException();
    }

    public void throw_InsufficientFundsException_with_FunctionalErrorCode() throws InsufficientFundsException {
        throw new InsufficientFundsException(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE);
    }

    public void throw_InsufficientFundsException_with_error_message() throws InsufficientFundsException {
        throw new InsufficientFundsException("Something went wrong!");
    }

    public void throw_TransferInterruptedException() throws TransferInterruptedException {
        throw new TransferInterruptedException();
    }

    public void throw_TransferInterruptedException_with_FunctionalErrorCode() throws TransferInterruptedException {
        throw new TransferInterruptedException(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND);
    }

    public void throw_TransferInterruptedException_with_error_message() throws TransferInterruptedException {
        throw new TransferInterruptedException("Something went wrong!");
    }

    public void throw_TransferRollbackException() throws TransferRollbackException {
        throw new TransferRollbackException();
    }

    public void throw_TransferRollbackException_with_FunctionalErrorCode() throws TransferRollbackException {
        throw new TransferRollbackException(FunctionalErrorCode.COULD_NOT_TRANSFER);
    }

    public void throw_TransferRollbackException_with_error_message() throws TransferRollbackException {
        throw new TransferRollbackException("Something went wrong!");
    }
}