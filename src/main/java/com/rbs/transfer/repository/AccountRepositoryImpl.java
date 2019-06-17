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
        return accounts.get(accountNo);
    }

    @Override
    public BigDecimal withdraw(Account sourceAccount, BigDecimal amount) {
        BigDecimal balance = sourceAccount.getBalance().subtract(amount);
        Account account = sourceAccount.update(balance);
        accounts.put(sourceAccount.getAccountNo(), account);
        return balance;
    }

    @Override
    public BigDecimal deposit(Account destinationAccount, BigDecimal amount) {
        BigDecimal balance = destinationAccount.getBalance().add(amount);
        Account account = destinationAccount.update(balance);
        accounts.put(destinationAccount.getAccountNo(), account);
        return balance;
    }
}
