package com.rbs.transfer.utils;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class FunctionalErrorCodeTest {
    @Test
    public void should_return_amount_can_not_be_null() {
        FunctionalErrorCode actual = FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL;
        assertEquals(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL, actual);
    }

    @Test
    public void should_return_amount_should_be_bigger_then_zero() {
        FunctionalErrorCode actual = FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO;
        assertEquals(FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO, actual);
    }

    @Test
    public void should_return_source_account_not_found() {
        FunctionalErrorCode actual = FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND;
        assertEquals(FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND, actual);
    }

    @Test
    public void should_return_source_account_insufficient_funds() {
        FunctionalErrorCode actual = FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE;
        assertEquals(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE, actual);
    }

    @Test
    public void should_return_destination_account_not_found() {
        FunctionalErrorCode actual = FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND;
        assertEquals(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND, actual);
    }

    @Test
    public void should_return_could_not_transfer() {
        FunctionalErrorCode actual = FunctionalErrorCode.COULD_NOT_TRANSFER;
        assertEquals(FunctionalErrorCode.COULD_NOT_TRANSFER, actual);
    }

    @Test
    public void should_return_internal_error() {
        FunctionalErrorCode actual = FunctionalErrorCode.INTERNAL_ERROR;
        assertEquals(FunctionalErrorCode.INTERNAL_ERROR, actual);
    }
}
