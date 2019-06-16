package com.rbs.transfer.controller;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;


@RunWith(SpringRunner.class)
public class TransferControllerTest {

    private MockMvc mvc;

    @InjectMocks
    private TransferController controller;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void transfer_should_throw_NotImplementedException() {
        TransferController transferController = new TransferController();
        transferController.transfer();
    }
}