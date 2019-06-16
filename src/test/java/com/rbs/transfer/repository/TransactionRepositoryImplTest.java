package com.rbs.transfer.repository;

import com.rbs.transfer.domain.Transaction;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

public class TransactionRepositoryImplTest {

    @Mock
    private Transaction mockedTransaction;

    @InjectMocks
    private TransactionRepositoryImpl transactionRepository;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void find_should_return_existing_transaction() {
        String sourceAccountNo = "16854478";
        String destinationAccountNo = "24477441";
        BigDecimal transferAmount = new BigDecimal(100.10);

        UUID id = this.transactionRepository.add(sourceAccountNo, destinationAccountNo, transferAmount);

        Assertions.assertThat(id).isInstanceOf(UUID.class);
        assertNotNull(id);

        Transaction actual = this.transactionRepository.findById(id);

        assertEquals(sourceAccountNo, actual.getSourceAccountNo());
        assertEquals(destinationAccountNo, actual.getDestinationAccountNo());
        assertEquals(transferAmount, actual.getAmount());
    }

    @Test
    public void find_should_return_list_of_transactions() {
        String sourceAccountNo_1 = "17854478";
        String destinationAccountNo_1 = "25477441";

        String sourceAccountNo_2 = "81454478";
        String destinationAccountNo_2 = "91427441";

        BigDecimal transferAmount = new BigDecimal(100.10);
        BigDecimal transferAmount2 = new BigDecimal(200.20);

        this.transactionRepository.add(sourceAccountNo_1, destinationAccountNo_1, transferAmount);
        this.transactionRepository.add(sourceAccountNo_1, destinationAccountNo_1, transferAmount2);
        this.transactionRepository.add(sourceAccountNo_2, destinationAccountNo_2, transferAmount);
        this.transactionRepository.add(sourceAccountNo_2, destinationAccountNo_2, transferAmount2);
        this.transactionRepository.add(sourceAccountNo_1, destinationAccountNo_2, transferAmount);
        this.transactionRepository.add(sourceAccountNo_2, destinationAccountNo_1, transferAmount);

        List<Transaction> transactionList = this.transactionRepository.find(sourceAccountNo_1, destinationAccountNo_1);

        Assertions.assertThat(transactionList.size()).isEqualTo(2);

        transactionList.stream().forEach(transaction -> {
            assertEquals(sourceAccountNo_1, transaction.getSourceAccountNo());
            assertEquals(destinationAccountNo_1, transaction.getDestinationAccountNo());
        });
    }
}
