package com.rbs.transfer.utils;

public enum FunctionalErrorCode {

    AMOUNT_CAN_NOT_BE_NULL("Amount can not be null"),
    AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO("Amount should be bigger than zero."),
    SOURCE_ACCOUNT_NOT_FOUND("Source account not found."),
    SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE("Source account does not has enough balance."),
    DESTINATION_ACCOUNT_NOT_FOUND("Destination account not found."),
    COULD_NOT_TRANSFER("Could not transfer."),
    INTERNAL_ERROR("Internal Error");

    private final String errorMessage;

    FunctionalErrorCode(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    @Override
    public String toString(){
        return errorMessage;
    }
}