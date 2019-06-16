package com.rbs.transfer.controller;

import com.rbs.transfer.service.TransferService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
public class TransferControllerTest {

    private MockMvc mvc;

    @Mock
    private TransferService transferService;

    @InjectMocks
    private TransferController controller;

    @Before
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(controller).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void transfer_should_return_bad_request() {
        TransferController transferController = new TransferController(transferService);
        ResponseEntity actual = transferController.transfer();
        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }
}