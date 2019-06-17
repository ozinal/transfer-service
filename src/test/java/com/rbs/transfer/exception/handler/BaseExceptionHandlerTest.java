package com.rbs.transfer.exception.handler;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;

import static org.junit.Assert.assertEquals;

public class BaseExceptionHandlerTest {

    @InjectMocks
    private BaseExceptionHandler handler;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void handleMissingServletRequestParameter_return_failed_response() {

        String paramName = "sourceAccountNo";
        String paramType = "String";

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        MissingServletRequestParameterException ex = new MissingServletRequestParameterException(paramName, paramType);
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(httpServletRequest);

        ResponseEntity actual = this.handler.handleMissingServletRequestParameter(ex, headers, HttpStatus.BAD_GATEWAY, request);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void handleIllegalExceptions_return_failed_response() {
        RuntimeException ex = new IllegalArgumentException();
        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(httpServletRequest);

        ResponseEntity actual = this.handler.handleIllegalExceptions(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, actual.getStatusCode());
    }

    @Test
    public void handleMethodArgumentNotValid_return_failed_response() {

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        MethodParameter methodParameter = Mockito.mock(MethodParameter.class);
        BindingResult bindingResult = Mockito.mock(BindingResult.class);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(methodParameter, bindingResult);

        MockHttpServletRequest httpServletRequest = new MockHttpServletRequest();
        WebRequest request = new ServletWebRequest(httpServletRequest);

        ResponseEntity actual = this.handler.handleMethodArgumentNotValid(ex, headers, HttpStatus.BAD_GATEWAY, request);

    }
}
