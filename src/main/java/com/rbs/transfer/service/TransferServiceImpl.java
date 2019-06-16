package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.domain.Transaction;
import com.rbs.transfer.repository.AccountRepository;
import com.rbs.transfer.repository.TransactionRepository;
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

    @Override
    public boolean transfer(final InputData input)
            throws Exception {
        LOG.info("Transfer started, {}", input);
        this.transferValidator.validateAmount(input.getAmount());
        Account sourceAccount = this.accountRepository.findById(input.getSourceAccountNo());
        this.transferValidator.validateSourceAccount(sourceAccount, input.getAmount());
        Account destinationAccount = this.accountRepository.findById(input.getDestinationAccountNo());
        this.transferValidator.validateDestinationAccount(destinationAccount);

        return doTransfer(sourceAccount, destinationAccount, input.getAmount());
    }

    private boolean doTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amount)
            throws Exception {
        boolean withdrawn = false;
        boolean deposited = false;
        UUID transactionId = null;
        try {
            transactionId = this.transactionRepository.add(sourceAccount.getAccountNo(), destinationAccount.getAccountNo(), amount);
            if (transactionId == null) {
                LOG.error("Transfer failed, source account: {}, destination account: {}, amount: {}", sourceAccount, destinationAccount, amount);
                throw new Exception("Could not transfer.");
            }
            this.accountRepository.withdraw(sourceAccount, amount);
            withdrawn = true;
            this.accountRepository.deposit(destinationAccount, amount);
            deposited = true;
            this.transactionRepository.succeed(transactionId);
        } catch (Exception ex) {
            rollBack(transactionId, sourceAccount, destinationAccount, amount, withdrawn, deposited);
            throw ex;
        }
        LOG.info("Transaction succeed, transaction ID: {}", transactionId);
        return true;
    }

    @Override
    public void rollBack(UUID transactionId,
                         Account sourceAccount,
                         Account destinationAccount,
                         BigDecimal amount,
                         boolean withdrawn,
                         boolean deposited) throws Exception {
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
            throw new Exception(ex.getMessage());
        }
    }
}
