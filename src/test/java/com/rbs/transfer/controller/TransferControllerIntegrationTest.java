package com.rbs.transfer.controller;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.exception.*;
import com.rbs.transfer.repository.AccountRepository;
import com.rbs.transfer.repository.TransactionRepository;
import com.rbs.transfer.service.TransferService;
import com.rbs.transfer.service.TransferServiceImpl;
import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.transfer.validator.TransferValidator;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TransferControllerIntegrationTest {

    private String sourceAccount = "89673345";
    private String destinationAccount = "76667839";

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private TransferValidator transferValidator;

    private TransferService transferService;

    private TransferController controller;

    @Before
    public void setUp() {
        this.transferService = new TransferServiceImpl(this.accountRepository, this.transactionRepository, this.transferValidator);
        this.controller = new TransferController(this.transferService);

        // Create and deposit accounts
        Account sourceAccount = new Account(this.sourceAccount, BigDecimal.ZERO);
        this.accountRepository.deposit(sourceAccount, new BigDecimal("1000.00"));

        Account destinationAccount = new Account(this.destinationAccount, BigDecimal.ZERO);
        this.accountRepository.deposit(destinationAccount, new BigDecimal("1000.00"));
    }

    @Test
    public void should_return_success_response_when_valid_params_passed() throws AccountException, TransferRollbackException {
        InputData input = new InputData();
        input.setSourceAccountNo(this.sourceAccount);
        input.setDestinationAccountNo(this.destinationAccount);
        input.setAmount(BigDecimal.TEN);

        ResponseEntity actual = this.controller.transfer(input);

        BigDecimal remainingSourceAccountBalance = this.accountRepository.findById(this.sourceAccount).getBalance();
        BigDecimal expectedSourceAccountBalance = new BigDecimal("1000.00").subtract(BigDecimal.TEN);

        BigDecimal remainingDestinationAccountBalance = this.accountRepository.findById(this.destinationAccount).getBalance();
        BigDecimal expectedDestinationAccountBalance = new BigDecimal("1000.00").add(BigDecimal.TEN);

        assertEquals(HttpStatus.NO_CONTENT, actual.getStatusCode());
        assertEquals(expectedSourceAccountBalance, remainingSourceAccountBalance);
        assertEquals(expectedDestinationAccountBalance, remainingDestinationAccountBalance);
    }

    @Test
    public void should_return_failed_response_when_source_account_does_not_exists() throws AccountException, TransferRollbackException {
        InputData input = new InputData();
        input.setSourceAccountNo("84767881");
        input.setDestinationAccountNo(this.destinationAccount);
        input.setAmount(BigDecimal.TEN);

        this.expectedException.expect(AccountNotFoundException.class);
        this.expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND.toString()));

        this.controller.transfer(input);
    }

    @Test
    public void should_return_failed_response_when_destination_account_does_not_exists() throws AccountException, TransferRollbackException {
        InputData input = new InputData();
        input.setSourceAccountNo(this.sourceAccount);
        input.setDestinationAccountNo("19783678");
        input.setAmount(BigDecimal.TEN);

        this.expectedException.expect(AccountNotFoundException.class);
        this.expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND.toString()));

        this.controller.transfer(input);
    }

    @Test
    public void should_return_failed_response_when_insufficient_funds_in_source_account() throws AccountException, TransferRollbackException {
        InputData input = new InputData();
        input.setSourceAccountNo(this.sourceAccount);
        input.setDestinationAccountNo(this.destinationAccount);
        input.setAmount(new BigDecimal("1010.00"));

        this.expectedException.expect(InsufficientFundsException.class);
        this.expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE.toString()));

        this.controller.transfer(input);
    }

    @Test
    public void should_return_failed_response_when_zero_amount_passed() throws AccountException, TransferRollbackException {
        InputData input = new InputData();
        input.setSourceAccountNo(this.sourceAccount);
        input.setDestinationAccountNo(this.destinationAccount);
        input.setAmount(BigDecimal.ZERO);

        this.expectedException.expect(IllegalAmountException.class);
        this.expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO.toString()));

        this.controller.transfer(input);
    }
}