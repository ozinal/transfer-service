package com.rbs.transfer.domain;

import java.math.BigDecimal;

public class Account {

    private final String accountNo;

    private final BigDecimal balance;

    public Account(String accountNo, BigDecimal balance) {
        this.accountNo = accountNo;
        this.balance = balance;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public Account update(BigDecimal balance) {
        return new Account(this.accountNo, balance);
    }
}