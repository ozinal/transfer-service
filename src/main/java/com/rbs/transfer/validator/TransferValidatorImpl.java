package com.rbs.transfer.validator;

import java.math.BigDecimal;

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
}
