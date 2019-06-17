package com.rbs.transfer.validator;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.exception.AccountNotFoundException;
import com.rbs.transfer.exception.IllegalAmountException;
import com.rbs.transfer.exception.InsufficientFundsException;

import java.math.BigDecimal;

public interface TransferValidator {
    void validateAmount(BigDecimal amount) throws IllegalAmountException;
    void validateSourceAccount(Account sourceAccount, BigDecimal amount) throws AccountNotFoundException, InsufficientFundsException;
    void validateDestinationAccount(Account destinationAccount) throws AccountNotFoundException;
}
