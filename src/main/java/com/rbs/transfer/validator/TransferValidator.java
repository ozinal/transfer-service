package com.rbs.transfer.validator;


import java.math.BigDecimal;

public interface TransferValidator {
    void validateAmount(BigDecimal amount) throws Exception;
}
