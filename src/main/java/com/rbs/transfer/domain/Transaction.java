package com.rbs.transfer.domain;

import java.math.BigDecimal;
import java.util.UUID;

public class Transaction {

    private final UUID id;

    private final String sourceAccountNo;

    private final String destinationAccountNo;

    private final BigDecimal amount;
    private final boolean isSucceed;
    private final boolean isCompleted;

    public Transaction(UUID id,
                       String sourceAccountNo,
                       String destinationAccountNo,
                       BigDecimal amount,
                       boolean isSucceed,
                       boolean isCompleted) {
        this.id = id;
        this.sourceAccountNo = sourceAccountNo;
        this.destinationAccountNo = destinationAccountNo;
        this.amount = amount;
        this.isSucceed = isSucceed;
        this.isCompleted = isCompleted;
    }

    public Transaction(String sourceAccountNo,
                       String destinationAccountNo,
                       BigDecimal amount,
                       boolean isSucceed,
                       boolean isCompleted) {
        this(UUID.randomUUID(), sourceAccountNo, destinationAccountNo, amount, isSucceed, isCompleted);
    }

    public UUID getId() {
        return id;
    }

    public String getSourceAccountNo() {
        return sourceAccountNo;
    }

    public String getDestinationAccountNo() {
        return destinationAccountNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isSucceed() {
        return isCompleted && isSucceed;
    }

    public boolean isFailed() {
        return isCompleted && !isSucceed;
    }
}