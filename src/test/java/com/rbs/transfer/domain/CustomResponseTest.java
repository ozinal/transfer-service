package com.rbs.transfer.domain;

import com.rbs.transfer.utils.FunctionalErrorCode;
import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class CustomResponseTest {

    @Test
    public void succeed_should_return_succeed_response() {
        CustomResponse actual = CustomResponse.succeed();
        assertTrue(actual.isSuccess());
        assertEquals(0, actual.getErrors().size());
    }

    @Test
    public void failed_should_return_failed_response_with_single_error() {
        String message = "Some error message";
        CustomResponse actual = CustomResponse.failed(message);
        assertFalse(actual.isSuccess());
        assertTrue(actual.getErrors().contains(message));
    }

    @Test
    public void failed_should_return_failed_response_with_multiple_errors() {
        List<String> errors= Arrays.asList("Error1","Error2");
        CustomResponse actual = CustomResponse.failed(errors);
        assertFalse(actual.isSuccess());
        assertEquals(errors,actual.getErrors());
    }

    @Test
    public void isCompleted_should_return_success_response() {
        CustomResponse actual = CustomResponse.succeed();
        assertTrue(actual.isSuccess());
    }

    @Test
    public void isFailed_should_return_failed_response_with_func_error_code() {
        CustomResponse actual = CustomResponse.failed(FunctionalErrorCode.COULD_NOT_TRANSFER);
        assertTrue(actual.isFailed());
        Assertions.assertThat(actual.getErrors()).containsOnly(FunctionalErrorCode.COULD_NOT_TRANSFER.toString());
    }

    @Test
    public void isCompleted_should_return_failed_validation() {
        CustomResponse actual = CustomResponse.failed(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL);
        assertTrue(actual.isCompleted());
    }

    @Test
    public void notCompleted_should_return_failed_response_without_func_err_code() {
        CustomResponse actual = CustomResponse.notCompleted();
        assertFalse(actual.isCompleted());
        assertFalse(actual.isSuccess());
        assertFalse(actual.isFailed());
        Assertions.assertThat(actual.getErrors()).isEmpty();
    }
}