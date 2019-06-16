package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Transaction;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public interface TransactionRepository {

    Transaction findById(UUID id);

    List<Transaction> find(String sourceAccountNo, String destinationAccountNo);

    UUID add(String sourceAccountNo, String destinationAccountNo, BigDecimal amount);
}
