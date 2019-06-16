package com.rbs.transfer.service;

import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.validator.TransferValidator;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class TransferServiceImplTest {

    @Mock
    private InputData mockedInputData;

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
}