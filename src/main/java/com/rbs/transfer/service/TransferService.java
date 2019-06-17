package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.exception.AccountException;
import com.rbs.transfer.exception.TransferRollbackException;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferService {
    boolean transfer(final InputData input)
            throws AccountException, TransferRollbackException;

    void rollBack(UUID transactionId,
                  Account sourceAccount,
                  Account destinationAccount,
                  BigDecimal amount,
                  boolean withDrawn,
                  boolean deposited) throws TransferRollbackException;
}
