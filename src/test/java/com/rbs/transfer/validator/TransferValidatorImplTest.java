package com.rbs.transfer.validator;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.exception.AccountNotFoundException;
import com.rbs.transfer.exception.IllegalAmountException;
import com.rbs.transfer.exception.InsufficientFundsException;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransferValidatorImplTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Mock
    private Account mockedAccount;

    @InjectMocks
    private TransferValidatorImpl validator;

    @Test(expected = IllegalAmountException.class)
    public void validateAmount_should_throw_IllegalAmountException_when_amount_not_passed() throws IllegalAmountException {
        this.validator.validateAmount(null);
    }

    @Test(expected = IllegalAmountException.class)
    public void validateAmount_should_throw_IllegalAmountException_when_zero_amount_passed() throws IllegalAmountException {
        this.validator.validateAmount(BigDecimal.ZERO);
    }

    @Test
    public void validateAmount_should_validate_amount_when_valid_parameter_passed() throws IllegalAmountException {
        this.validator.validateAmount(BigDecimal.ONE);
    }

    @Test(expected = AccountNotFoundException.class)
    public void validateSourceAccount_should_throw_AccountNotFoundException_when_account_does_not_exits() throws AccountNotFoundException, InsufficientFundsException {
        this.validator.validateSourceAccount(null, BigDecimal.ONE);
    }

    @Test(expected = InsufficientFundsException.class)
    public void validateSourceAccount_should_throw_InsufficientFundsException_when_there_is_no_enough_balance() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(this.mockedAccount.getBalance()).thenReturn(BigDecimal.ONE);
        this.validator.validateSourceAccount(this.mockedAccount, BigDecimal.TEN);
    }

    @Test
    public void validateSourceAccount_should_validate_account_balance() throws AccountNotFoundException, InsufficientFundsException {
        Mockito.when(this.mockedAccount.getBalance()).thenReturn(BigDecimal.TEN);
        this.validator.validateSourceAccount(this.mockedAccount, BigDecimal.ONE);
    }

    @Test(expected = AccountNotFoundException.class)
    public void validateDestinationAccount_should_throw_AccountNotFoundException_when_account_does_not_exists() throws AccountNotFoundException {
        this.validator.validateDestinationAccount(null);
    }

    @Test
    public void validateDestinationAccount_should_validate_account() throws AccountNotFoundException {
        Mockito.when(this.mockedAccount.getAccountNo()).thenReturn("11220938");
        Mockito.when(this.mockedAccount.getBalance()).thenReturn(BigDecimal.TEN);
        this.validator.validateDestinationAccount(this.mockedAccount);
    }
}