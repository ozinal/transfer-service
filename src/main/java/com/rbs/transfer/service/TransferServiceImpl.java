package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.validator.TransferValidator;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Service
public class TransferServiceImpl implements TransferService {

    private TransferValidator transferValidator;

    public TransferServiceImpl(TransferValidator transferValidator) {
        this.transferValidator = transferValidator;
    }


    @Override
    public boolean transfer(final InputData input) throws Exception {
        this.transferValidator.validateAmount(input.getAmount());

        Account sourceAccount = new Account(input.getSourceAccountNo(), BigDecimal.ZERO);
        Account destinationAccoint = new Account(input.getDestinationAccountNo(), BigDecimal.ZERO);
        return this.doTransfer(sourceAccount, destinationAccoint, input.getAmount());
    }

    private boolean doTransfer(Account sourceAccount, Account destinationAccount, BigDecimal amount)
            throws Exception {
        return false;
    }
}
