package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Account;

import java.math.BigDecimal;

public interface AccountRepository  {
    Account findById(String accountNo);

    BigDecimal withdraw(Account sourceAccount, BigDecimal amount);

    BigDecimal deposit(Account destinationAccount, BigDecimal amount);
}