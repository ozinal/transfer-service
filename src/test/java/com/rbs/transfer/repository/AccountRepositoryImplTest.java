package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.exception.AccountNotFoundException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class AccountRepositoryImplTest {

    @Mock
    private Account mockedSourceAccount;

    @Mock
    private Account mockedDestinationAccount;

    @InjectMocks
    private AccountRepositoryImpl accountRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void find_should_return_account_which_exists() {

        String accountNo = "1878546";
        BigDecimal amount = BigDecimal.ZERO;
        BigDecimal depositAmount = new BigDecimal(1254.10);

        Account account = new Account(accountNo, amount);

        this.accountRepository.deposit(account, depositAmount);

        Account actual = this.accountRepository.findById(accountNo);
        assertEquals(depositAmount, actual.getBalance());
        assertEquals(accountNo, account.getAccountNo());
    }

    @Test
    public void withdraw_should_return_withdrawn_balance() {

        String accountNo = "1878546";
        BigDecimal balance = new BigDecimal(1254.10);
        BigDecimal withdrawAmount = new BigDecimal(10);

        Mockito.when(this.mockedSourceAccount.getAccountNo()).thenReturn(accountNo);
        Mockito.when(this.mockedSourceAccount.getBalance()).thenReturn(balance);

        BigDecimal remainingBalance = this.accountRepository.withdraw(mockedSourceAccount, withdrawAmount);

        BigDecimal expectedBalance = balance.subtract(withdrawAmount);

        assertEquals(expectedBalance, remainingBalance);
    }

    @Test
    public void deposit_should_return_deposited_balance() {
        String accountNo = "98745562";
        BigDecimal balance = BigDecimal.ZERO;
        BigDecimal depositAmount = new BigDecimal(1000.12);

        Mockito.when(this.mockedDestinationAccount.getAccountNo()).thenReturn(accountNo);
        Mockito.when(this.mockedDestinationAccount.getBalance()).thenReturn(balance);

        BigDecimal remainingBalance = this.accountRepository.deposit(mockedDestinationAccount, depositAmount);

        BigDecimal expectedBalance = balance.add(depositAmount);

        assertEquals(expectedBalance, remainingBalance);
    }

    @Test(expected = AccountNotFoundException.class)
    public void withdraw_should_throw_AccountNotFoundException_when_balance_update() {
        Mockito.when(this.mockedSourceAccount.getBalance()).thenReturn(BigDecimal.TEN);
        Mockito.doThrow(AccountNotFoundException.class).when(this.mockedSourceAccount).update(Mockito.any(BigDecimal.class));

        this.accountRepository.withdraw(this.mockedSourceAccount, BigDecimal.ONE);
    }


    @Test(expected = AccountNotFoundException.class)
    public void deposit_should_throw_AccountNotFoundException_when_balance_update() {
        Mockito.when(this.mockedDestinationAccount.getBalance()).thenReturn(BigDecimal.TEN);
        Mockito.doThrow(AccountNotFoundException.class).when(this.mockedDestinationAccount).update(Mockito.any(BigDecimal.class));

        this.accountRepository.withdraw(this.mockedDestinationAccount, BigDecimal.ONE);
    }
}
