package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Transaction;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public class TransactionRepositoryImpl implements TransactionRepository {
    private static Map<UUID, Transaction> transactions = new HashMap<>();

    @Override
    public Transaction findById(UUID id) {
        return transactions.get(id);
    }

    @Override
    public List<Transaction> find(String sourceAccountNo, String destinationAccountNo) {

        return transactions.values().stream()
                .filter(t -> t.getSourceAccountNo().equals(sourceAccountNo)
                        && t.getDestinationAccountNo().equals(destinationAccountNo))
                .collect(Collectors.toList());
    }

    @Override
    public UUID add(String sourceAccountNo, String destinationAccountNo, BigDecimal amount) {
        Transaction transaction = new Transaction(sourceAccountNo, destinationAccountNo, amount, true,false);
        UUID id = transaction.getId();
        transactions.put(id, transaction);
        return id;
    }

    @Override
    public boolean failed(UUID id) {
        if (transactions.containsKey(id)) {
            Transaction t = transactions.get(id).failed();
            transactions.remove(id);
            transactions.put(id, t);
            return true;
        }
        return false;
    }

    @Override
    public boolean succeed(UUID id) {
        if (transactions.containsKey(id)) {
            Transaction t = transactions.get(id).succeed();
            transactions.remove(id);
            transactions.put(id, t);
            return true;
        }
        return false;
    }

    @Override
    public boolean delete(UUID id) {
        if (transactions.containsKey(id)) {
            transactions.remove(id);
            return true;
        }
        return false;
    }
}
