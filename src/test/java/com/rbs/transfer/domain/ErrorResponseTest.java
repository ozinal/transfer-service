package com.rbs.transfer.domain;

import com.rbs.transfer.utils.FunctionalErrorCode;
import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class ErrorResponseTest {

    @Test
    public void should_return_valid_values_when_status_and_list_of_errors_passed() {
        List<String> errorList = Collections.singletonList(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL.toString());
        ErrorResponse actual = new ErrorResponse(true, errorList);

        assertTrue(actual.isStatus());
        assertEquals(errorList, actual.getErrors());
    }

    @Test
    public void should_return_valid_values_when_status_and_error_message_passed() {
        String errorMessage = FunctionalErrorCode.COULD_NOT_TRANSFER.toString();
        ErrorResponse actual = new ErrorResponse(true, errorMessage);

        assertTrue(actual.isStatus());
        assertEquals(errorMessage, actual.getErrorMessage());
    }

    @Test
    public void should_return_valid_values_when_status_and_func_error_code_passed() {
        ErrorResponse actual = new ErrorResponse(false, FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND);

        assertFalse(actual.isStatus());
        assertEquals(FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND, actual.getFunctionalErrorCode());
    }

    @Test
    public void should_return_valid_values_when_status_and_parameter_name_passed() {
        String errorMessage = FunctionalErrorCode.SOURCE_ACCOUNT_NOT_FOUND.toString();
        String parameterName = "sourceAccountNo";
        ErrorResponse actual = new ErrorResponse(false, errorMessage, parameterName);

        String expectedParameterName = parameterName.concat(" parameter is missing.");

        assertFalse(actual.isStatus());
        assertEquals(errorMessage, actual.getErrorMessage());
        assertEquals(expectedParameterName, actual.getParameterName());
    }
}
