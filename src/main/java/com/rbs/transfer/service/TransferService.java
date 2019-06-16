package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;

import java.math.BigDecimal;
import java.util.UUID;

public interface TransferService {
    boolean transfer(InputData input) throws Exception;
    void rollBack(UUID transactionId,
                  Account sourceAccount,
                  Account destinationAccount,
                  BigDecimal amount,
                  boolean withDrawn,
                  boolean deposited) throws Exception;
}
