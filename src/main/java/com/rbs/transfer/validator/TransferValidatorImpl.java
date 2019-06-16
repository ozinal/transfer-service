package com.rbs.transfer.validator;

import com.rbs.transfer.domain.Account;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class TransferValidatorImpl implements TransferValidator {
    @Override
    public void validateAmount(BigDecimal amount) throws Exception {
        if (amount == null) {
            throw new Exception("Amount can not be null.");
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new Exception("Amount should be bigger then zero.");
        }
    }

    @Override
    public void validateSourceAccount(Account sourceAccount, BigDecimal amount) throws Exception {
        if (sourceAccount == null) {
            throw new Exception("Source account not found.");
        }
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new Exception("Source account does not has enough balance.");
        }
    }

    @Override
    public void validateDestinationAccount(Account destinationAccount) throws Exception {
        if (destinationAccount == null) {
            throw new Exception("Destination account is not found.");
        }
    }
}