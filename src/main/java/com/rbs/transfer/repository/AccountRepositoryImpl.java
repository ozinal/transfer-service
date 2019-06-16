package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Account;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Repository
public class AccountRepositoryImpl implements AccountRepository {
    private static Map<String, Account> accounts = new HashMap<>();

    @Override
    public Account findById(String accountNo) {
        throw new UnsupportedOperationException();
    }

    @Override
    public BigDecimal withdraw(Account sourceAccount, BigDecimal amount) {
        return BigDecimal.ZERO;
    }

    @Override
    public BigDecimal deposit(Account destinationAccount, BigDecimal amount) {
        return BigDecimal.ZERO;
    }
}
