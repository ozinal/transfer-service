package com.rbs.transfer.controller;

import com.rbs.transfer.domain.InputData;
import com.rbs.transfer.exception.TransferRollbackException;
import com.rbs.transfer.exception.handler.CustomExceptionHandler;
import com.rbs.transfer.service.TransferService;
import org.hamcrest.CoreMatchers;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.nio.file.Files;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class TransferControllerTest {

    private MockMvc mvc;

    @Mock
    private TransferService mockedTransferService;

    private TransferController controller;

    public void setUp(TransferService mockedTransferService) {
        controller=new TransferController(mockedTransferService);
        mvc = MockMvcBuilders.standaloneSetup(controller).setControllerAdvice(new CustomExceptionHandler()).build();
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void controller_should_return_success_response_when_valid_params_passed() throws Exception, TransferRollbackException {
        when(mockedTransferService.transfer(any(InputData.class))).thenReturn(true);
        setUp(mockedTransferService);

        File file = ResourceUtils.getFile("classpath:core/transferPayload_200.json");
        String content = new String(Files.readAllBytes(file.toPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(MockMvcRequestBuilders.post("/v1/transfer")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(content)
        ).andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void should_return_failed_response_when_params_has_invalid_length() throws Exception, TransferRollbackException {
        when(mockedTransferService.transfer(any(InputData.class))).thenReturn(false);
        setUp(mockedTransferService);

        File file = ResourceUtils.getFile("classpath:core/transferInvalidLengthPayload_200.json");
        String content = new String(Files.readAllBytes(file.toPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(MockMvcRequestBuilders.post("/v1/transfer")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.containsInAnyOrder(
                        "destinationAccountNo : size must be between 8 and 10",
                        "sourceAccountNo : size must be between 8 and 10")));
    }

    @Test
    public void should_return_failed_response_when_params_has_not_passed() throws Exception, TransferRollbackException {
        when(mockedTransferService.transfer(any(InputData.class))).thenReturn(false);
        setUp(mockedTransferService);

        File file = ResourceUtils.getFile("classpath:core/transferInvalidPayload_200.json");
        String content = new String(Files.readAllBytes(file.toPath()));

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        mvc.perform(MockMvcRequestBuilders.post("/v1/transfer")
                .headers(headers)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(content))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", CoreMatchers.is(false)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.errors", Matchers.containsInAnyOrder(
                        "destinationAccountNo : must not be empty",
                        "amount : must not be null")));
    }
}
