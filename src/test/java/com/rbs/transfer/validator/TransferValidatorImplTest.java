package com.rbs.transfer.validator;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

public class TransferValidatorImplTest {

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @InjectMocks
    private TransferValidatorImpl validator;

    @Test(expected = Exception.class)
    public void validateAmount_should_throw_Exception_when_amount_not_passed() throws Exception {
        this.validator.validateAmount(null);
    }

    @Test(expected = Exception.class)
    public void validateAmount_should_throw_Exception_when_zero_amount_passed() throws Exception {
        this.validator.validateAmount(BigDecimal.ZERO);
    }

    @Test
    public void validateAmount_should_validate_amount_when_valid_parameter_passed() throws Exception {
        this.validator.validateAmount(BigDecimal.ONE);
    }
}