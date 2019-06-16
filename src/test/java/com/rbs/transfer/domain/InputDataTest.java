package com.rbs.transfer.domain;

import org.junit.Test;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;

public class InputDataTest {

    @Test
    public void should_return_assigned_values() {
        String sourceAccountNo = "92233456";
        String destinationAccountNo = "78233456";
        BigDecimal amount = BigDecimal.TEN;


        InputData actual = new InputData();
        actual.setSourceAccountNo(sourceAccountNo);
        actual.setDestinationAccountNo(destinationAccountNo);
        actual.setAmount(amount);

        assertEquals(sourceAccountNo, actual.getSourceAccountNo());
        assertEquals(destinationAccountNo, actual.getDestinationAccountNo());
        assertEquals(amount, actual.getAmount());
    }
}
