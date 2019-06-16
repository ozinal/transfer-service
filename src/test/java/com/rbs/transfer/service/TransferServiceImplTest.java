package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.domain.Transaction;
import com.rbs.transfer.repository.AccountRepositoryImpl;
import com.rbs.transfer.repository.TransactionRepository;
import com.rbs.transfer.validator.TransferValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

public class TransferServiceImplTest {

    @Mock
    private InputData mockedInputData;

    @Mock
    private Account mockedSourceAccount;

    @Mock
    private Account mockedDestinationAccount;

    @Mock
    private Transaction mockedTransaction;

    @Mock
    private AccountRepositoryImpl mockedAccountRepository;

    @Mock
    private TransactionRepository mockedTransactionRepository;

    @Mock
    private TransferValidator mockedTransferValidator;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void transfer_should_return_success_response() throws Exception {

        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(UUID.randomUUID());

        when(this.mockedAccountRepository.withdraw(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        when(this.mockedAccountRepository.deposit(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

        when(this.mockedTransactionRepository.succeed(any(UUID.class))).thenReturn(true);

        boolean actual = this.transferService.transfer(this.mockedInputData);

        verify(this.mockedTransferValidator, times(1)).validateAmount(any(BigDecimal.class));
        verify(this.mockedTransferValidator, times(1)).validateSourceAccount(any(Account.class), any(BigDecimal.class));
        verify(this.mockedTransferValidator, times(1)).validateDestinationAccount(Mockito.any(Account.class));
        verify(this.mockedAccountRepository, times(2)).findById(anyString());
        verify(this.mockedTransactionRepository, times(1)).add(Mockito.anyString(), Mockito.anyString(), Mockito.any(BigDecimal.class));

        verify(this.mockedAccountRepository, times(1)).withdraw(Mockito.any(Account.class), Mockito.any(BigDecimal.class));
        verify(this.mockedAccountRepository, times(1)).deposit(Mockito.any(Account.class), Mockito.any(BigDecimal.class));
        verify(this.mockedTransactionRepository, times(1)).succeed(Mockito.any(UUID.class));

        verifyNoMoreInteractions(this.mockedTransactionRepository);
        verifyNoMoreInteractions(this.mockedTransferValidator);
        verifyNoMoreInteractions(this.mockedAccountRepository);

        assertTrue(actual);
    }

    @Test(expected = Exception.class)
    public void transfer_should_throw_TransferInterruptedException_when_transaction_failed() throws Exception {

        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(null);

        this.transferService.transfer(this.mockedInputData);
    }

    @Test(expected = Exception.class)
    public void transfer_should_throw_IllegalAmountException_when_amount_validation_failed() throws Exception {
        String sourceAccountNo = "14785697";
        String destinationAccountNo = "18455691";

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(null);

        doThrow(Exception.class).when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));

        this.transferService.transfer(this.mockedInputData);
    }

    @Test(expected = Exception.class)
    public void transfer_should_throw_AccountNotFoundException_when_source_account_does_not_exists() throws Exception {

        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));
        doThrow(Exception.class).when(this.mockedAccountRepository).findById(anyString());

        this.transferService.transfer(this.mockedInputData);
    }

    @Test(expected = Exception.class)
    public void transfer_should_rollback_transaction_state_when_withdraw_failed() throws Exception {
        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(UUID.randomUUID());

        doThrow(Exception.class).when(this.mockedAccountRepository).withdraw(any(Account.class), any(BigDecimal.class));

        this.transferService.transfer(this.mockedInputData);
    }

    @Test(expected = Exception.class)
    public void transfer_should_throw_exception_when_when_deposit_failed() throws Exception {
        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(UUID.randomUUID());

        when(this.mockedAccountRepository.withdraw(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        doThrow(Exception.class).when(this.mockedAccountRepository).deposit(any(Account.class), any(BigDecimal.class));

        this.transferService.transfer(this.mockedInputData);
    }

    @Test(expected = Exception.class)
    public void rollback_should_throw_TransferRollbackException_when_rollback_state_failed() throws Exception {
        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(UUID.randomUUID());

        when(this.mockedAccountRepository.withdraw(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        when(this.mockedAccountRepository.deposit(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

        doThrow(Exception.class).when(this.mockedTransactionRepository).succeed(any(UUID.class));
        doThrow(Exception.class).when(this.mockedTransactionRepository).findById(Mockito.any(UUID.class));

        this.transferService.transfer(this.mockedInputData);

    }

    @Test
    public void rollback_should_rollback_when_trx_failed() throws Exception {
        String sourceAccountNo = "14785697";
        BigDecimal sourceAccountBalance = new BigDecimal(1000.00);

        String destinationAccountNo = "18455691";
        BigDecimal transferAmount = new BigDecimal(250.00);

        when(this.mockedInputData.getSourceAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedInputData.getDestinationAccountNo()).thenReturn(destinationAccountNo);
        when(this.mockedInputData.getAmount()).thenReturn(transferAmount);

        Transaction mockedTrx = mock(Transaction.class);
        Mockito.when(mockedTrx.getId()).thenReturn(UUID.randomUUID());
        Mockito.when(mockedTrx.getAmount()).thenReturn(BigDecimal.TEN);
        Mockito.when(mockedTrx.getSourceAccountNo()).thenReturn(sourceAccountNo);
        Mockito.when(mockedTrx.getDestinationAccountNo()).thenReturn(destinationAccountNo);

        when(this.mockedSourceAccount.getAccountNo()).thenReturn(sourceAccountNo);
        when(this.mockedSourceAccount.getBalance()).thenReturn(sourceAccountBalance);

        doNothing().when(this.mockedTransferValidator).validateAmount(any(BigDecimal.class));
        doNothing().when(this.mockedTransferValidator).validateDestinationAccount(any(Account.class));

        when(this.mockedAccountRepository.findById(anyString())).thenReturn(mockedSourceAccount);
        when(this.mockedTransactionRepository.add(anyString(), anyString(), any(BigDecimal.class))).thenReturn(UUID.randomUUID());

        when(this.mockedAccountRepository.withdraw(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);
        when(this.mockedAccountRepository.deposit(any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

        when(this.mockedTransactionRepository.succeed(any(UUID.class))).thenReturn(true);

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTrx);

        when(this.mockedAccountRepository.deposit(Mockito.any(Account.class), any(BigDecimal.class))).thenReturn(BigDecimal.TEN);

        this.transferService.transfer(this.mockedInputData);

        verify(this.mockedAccountRepository, times(1)).deposit(any(Account.class),any(BigDecimal.class));
        verify(this.mockedAccountRepository, times(1)).withdraw(any(Account.class),any(BigDecimal.class));

    }

    @Test
    public void rollBack_should_return_scrollback_withdrawn_and_deposited_funds() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited);

        verify(this.mockedTransactionRepository, times(1)).findById(any(UUID.class));
        verify(this.mockedTransactionRepository, times(1)).delete(any(UUID.class));
        verify(this.mockedAccountRepository, times(1)).withdraw(any(Account.class), any(BigDecimal.class));
        verify(this.mockedAccountRepository, times(1)).deposit(any(Account.class), any(BigDecimal.class));

        verifyZeroInteractions(this.mockedTransferValidator);
        verifyNoMoreInteractions(this.mockedTransactionRepository);
        verifyNoMoreInteractions(this.mockedAccountRepository);
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_Exception_when_repository_failed() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);
        doThrow(Exception.class).when(this.mockedTransactionRepository).findById(any(UUID.class));

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_TransferRollbackException_when_repository_attempting_find_transaction() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);
        doThrow(Exception.class).when(this.mockedTransactionRepository).findById(any(UUID.class));

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_TransferRollbackException_when_deposit_process_failed() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);
        doThrow(Exception.class).when(this.mockedAccountRepository).deposit(any(Account.class), any(BigDecimal.class));

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_TransferRollbackException_when_delete_process_failed() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);
        doThrow(Exception.class).when(this.mockedAccountRepository).deposit(any(Account.class), any(BigDecimal.class));

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_TransferRollbackException_when_withdraw_process_failed() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        when(this.mockedTransactionRepository.findById(Mockito.any(UUID.class))).thenReturn(mockedTransaction);
        doThrow(Exception.class).when(this.mockedAccountRepository).withdraw(any(Account.class), any(BigDecimal.class));

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }
}