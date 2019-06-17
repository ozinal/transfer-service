package com.rbs.transfer.validator;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.exception.AccountNotFoundException;
import com.rbs.transfer.exception.IllegalAmountException;
import com.rbs.transfer.exception.InsufficientFundsException;
import com.rbs.transfer.utils.FunctionalErrorCode;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

/**
 * Validates input parameters including source and destination account.
 * Checks whether source/destination accounts exists or not.
 * Also checks source account's balance for sufficient funds.
 */
@Component
public class TransferValidatorImpl implements TransferValidator {
    @Override
    public void validateAmount(BigDecimal amount) throws IllegalAmountException {
        if (amount == null) {
            throw new IllegalAmountException(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL);
        }
        if (amount.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalAmountException(FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO);
        }
    }

    @Override
    public void validateSourceAccount(Account sourceAccount, BigDecimal amount) throws AccountNotFoundException, InsufficientFundsException {
        if (sourceAccount == null) {
            throw new AccountNotFoundException(FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND);
        }
        if (sourceAccount.getBalance().compareTo(amount) < 0) {
            throw new InsufficientFundsException(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE);
        }
    }

    @Override
    public void validateDestinationAccount(Account destinationAccount) throws AccountNotFoundException {
        if (destinationAccount == null) {
            throw new AccountNotFoundException(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND);
        }
    }
}
