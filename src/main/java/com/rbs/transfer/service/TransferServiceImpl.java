package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.domain.Transaction;
import com.rbs.transfer.exception.AccountException;
import com.rbs.transfer.exception.TransferInterruptedException;
import com.rbs.transfer.exception.TransferRollbackException;
import com.rbs.transfer.repository.AccountRepository;
import com.rbs.transfer.repository.TransactionRepository;
import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.transfer.validator.TransferValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Implements whole lifecycle of transaction state.
 * Rollback model scrollback the withdrawn and deposited amount.
 *
 * {@link TransferService}
 */
@Service
public class TransferServiceImpl implements TransferService {

    private final static Logger LOG = LoggerFactory.getLogger(TransferServiceImpl.class);

    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final TransferValidator transferValidator;

    public TransferServiceImpl(AccountRepository accountRepository,
                               TransactionRepository transactionRepository,
                               TransferValidator transferValidator) {
        this.accountRepository = accountRepository;
        this.transactionRepository = transactionRepository;
        this.transferValidator = transferValidator;
    }

    /**
     * The method use validator to do relevant transfer amount checks.
     * Checks both source and destination account's existing states.
     * Also does balance checks for source account and make sure it has sufficient funds available to deposit destination account.
     * Responsible to create the transaction and complete it whether transaction state going to be fail or success.
     *
     * @param input
     * @return
     * @throws AccountException
     * @throws TransferRollbackException
     */
    @Override
    public boolean transfer(final InputData input)
            throws AccountException, TransferRollbackException {
        LOG.info("Transfer started, {}", input);
        this.transferValidator.validateAmount(input.getAmount());
        Account sourceAccount = this.accountRepository.findById(input.getSourceAccountNo());
        this.transferValidator.validateSourceAccount(sourceAccount, input.getAmount());
        Account destinationAccount = this.accountRepository.findById(input.getDestinationAccountNo());
        this.transferValidator.validateDestinationAccount(destinationAccount);

        return doTransfer(sourceAccount, destinationAccount, input.getAmount());
    }

    /**
     * The method creates actual transaction after validation checks.
     * Responsible about transaction state whether it's going to be fail or successful.
     *
     * @param sourceAccount
     * @param destinationAccount
     * @param amount
     * @return
     * @throws AccountException
     * @throws TransferRollbackException
     */
    private boolean doTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amount)
            throws AccountException, TransferRollbackException {
        boolean withdrawn = false;
        boolean deposited = false;
        UUID transactionId = null;
        try {
            transactionId = this.transactionRepository.add(sourceAccount.getAccountNo(), destinationAccount.getAccountNo(), amount);
            if (transactionId == null) {
                LOG.error("Transfer failed, source account: {}, destination account: {}, amount: {}", sourceAccount, destinationAccount, amount);
                throw new TransferInterruptedException(FunctionalErrorCode.COULD_NOT_TRANSFER);
            }
            this.accountRepository.withdraw(sourceAccount, amount);
            withdrawn = true;
            this.accountRepository.deposit(destinationAccount, amount);
            deposited = true;
            this.transactionRepository.succeed(transactionId);
        } catch (AccountException ex) {
            rollBack(transactionId, sourceAccount, destinationAccount, amount, withdrawn, deposited);
            throw ex;
        } catch (Exception ex) {
            rollBack(transactionId, sourceAccount, destinationAccount, amount, withdrawn, deposited);
            throw new AccountException(ex.getMessage());
        }
        LOG.info("Transaction succeed, transaction ID: {}", transactionId);
        return true;
    }

    /**
     * Rollback is responsible to get the transaction back to the previous state
     * It checks whether deposit or withdraw process completed or not.
     * If the amount withdrawn, it puts the amount back to the source account.
     * And if the amount deposited, it withdraw the amount back from destination account.
     *
     *
     * @param transactionId
     * @param sourceAccount
     * @param destinationAccount
     * @param amount
     * @param withdrawn
     * @param deposited
     * @throws TransferRollbackException
     */
    @Override
    public void rollBack(UUID transactionId,
                         Account sourceAccount,
                         Account destinationAccount,
                         BigDecimal amount,
                         boolean withdrawn,
                         boolean deposited) throws TransferRollbackException {
        try {
            LOG.error("Rollback started, trxId: {}, source account: {}, destination account: {}",transactionId, sourceAccount, destinationAccount);
            Transaction transaction = this.transactionRepository.findById(transactionId);
            if (transaction != null) {
                if (withdrawn)
                    this.accountRepository.deposit(sourceAccount, amount);
                if (deposited)
                    this.accountRepository.withdraw(destinationAccount, amount);
                this.transactionRepository.delete(transactionId);
            }
        } catch (Exception ex) {
            LOG.error("Rollback state failed, {}", ex);
            throw new TransferRollbackException(ex.getMessage());
        }
    }
}
