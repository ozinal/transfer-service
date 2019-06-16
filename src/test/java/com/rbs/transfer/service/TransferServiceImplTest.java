package com.rbs.transfer.service;

import org.junit.Test;

import static org.junit.Assert.*;

public class TransferServiceImplTest {

    @Test
    public void transfer_should_return_false() {
        TransferService transferService=new TransferServiceImpl();
        boolean actual = transferService.transfer();
        assertFalse(actual);
    }
}