package com.rbs.transfer.service;

import com.rbs.transfer.domain.Account;
import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.validator.TransferValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

public class TransferServiceImplTest {

    @Mock
    private InputData mockedInputData;

    @Mock
    private Account mockedSourceAccount;

    @Mock
    private Account mockedDestinationAccount;

    @Mock
    private TransferValidator mockedTransferValidator;

    @InjectMocks
    private TransferServiceImpl transferService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void transfer_should_return_false() throws Exception {

        Mockito.when(this.mockedInputData.getSourceAccountNo()).thenReturn("82973847");
        Mockito.when(this.mockedInputData.getDestinationAccountNo()).thenReturn("67663789");
        Mockito.when(this.mockedInputData.getAmount()).thenReturn(BigDecimal.TEN);

        Mockito.doNothing().when(this.mockedTransferValidator).validateAmount(Mockito.any());

        boolean actual = this.transferService.transfer(mockedInputData);
        assertFalse(actual);
    }

    @Test(expected = Exception.class)
    public void rollBack_throw_TransferRollbackException_when_withdraw_process_failed() throws Exception {
        UUID id = UUID.randomUUID();
        BigDecimal rollbackAmount = BigDecimal.TEN;
        boolean witdrawn = true;
        boolean deposited = true;

        this.transferService.rollBack(id,this.mockedSourceAccount, this.mockedDestinationAccount,rollbackAmount, witdrawn, deposited );
    }
}