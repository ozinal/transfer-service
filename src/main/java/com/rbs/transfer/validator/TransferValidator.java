package com.rbs.transfer.validator;


import com.rbs.transfer.domain.Account;

import java.math.BigDecimal;

public interface TransferValidator {
    void validateAmount(BigDecimal amount) throws Exception;
    void validateSourceAccount(Account sourceAccount, BigDecimal amount) throws Exception;
    void validateDestinationAccount(Account destinationAccount) throws Exception;
}
