package com.rbs.transfer.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AccountTest {

    @Test
    public void constructor_should_assign_all_values() {
        String accountNo = "17829838";
        BigDecimal balance = new BigDecimal(1250.00);
        Account account = new Account(accountNo, balance);
        assertEquals(accountNo,account.getAccountNo());
        assertEquals(balance,account.getBalance());
    }

    @Test
    public void update_should_return_updated_amount() {
        String accountNo = "98541123";
        BigDecimal balance = new BigDecimal(2250.12);
        BigDecimal newBalance = new BigDecimal(3250.12);

        Account account = new Account(accountNo, balance);
        Account updatedAccount = account.update(newBalance);

        assertEquals(newBalance, updatedAccount.getBalance());
        assertFalse(updatedAccount.equals(account));
    }
}