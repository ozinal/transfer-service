package com.rbs.transfer.exception.handler;

import com.rbs.transfer.domain.ErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Responsible for common exception handling can cause by any reason across framework.
 *
 * Extends {@link ResponseEntityExceptionHandler}
 */
public class BaseExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value= { IllegalArgumentException.class, IllegalStateException.class })
    protected ResponseEntity handleIllegalExceptions(RuntimeException ex, WebRequest request) {
        final ErrorResponse errorResponse= new ErrorResponse(false, ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity handleMissingServletRequestParameter(
            MissingServletRequestParameterException ex, HttpHeaders headers,
            HttpStatus status, WebRequest request) {

        final ErrorResponse errorResponse= new ErrorResponse(false, ex.getLocalizedMessage(), ex.getParameterName());
        return new ResponseEntity(errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @Override
    protected ResponseEntity handleMethodArgumentNotValid(
            MethodArgumentNotValidException ex,
            HttpHeaders headers,
            HttpStatus status,
            WebRequest request) {
        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().stream().forEach(x -> {
            errors.add(x.getField() + " : " + x.getDefaultMessage());
        });

        ex.getBindingResult().getGlobalErrors().stream().forEach(x -> {
            errors.add(x.getObjectName() + " : " + x.getDefaultMessage());
        });

        final ErrorResponse errorResponse = new ErrorResponse(false, errors);
        return handleExceptionInternal(
                ex, errorResponse, headers, HttpStatus.BAD_REQUEST, request);
    }
}