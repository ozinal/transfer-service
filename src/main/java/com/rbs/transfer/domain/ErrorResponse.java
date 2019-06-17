package com.rbs.transfer.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.rbs.transfer.utils.FunctionalErrorCode;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResponse {

    private final boolean status;
    private final String errorMessage;
    private final String parameterName;
    private final List<String> errors;
    private final FunctionalErrorCode functionalErrorCode;

    public ErrorResponse(final boolean status, final List<String> errors) {
        this.status = status;
        this.errorMessage = null;
        this.parameterName = null;
        this.errors = errors;
        this.functionalErrorCode = null;
    }

    public ErrorResponse(final boolean status, final String errorMessage) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.parameterName = null;
        this.errors = null;
        this.functionalErrorCode = null;
    }


    public ErrorResponse(final boolean status, final FunctionalErrorCode functionalErrorCode) {
        this.status = status;
        this.errorMessage = null;
        this.parameterName = null;
        this.errors = null;
        this.functionalErrorCode = functionalErrorCode;
    }

    public ErrorResponse(final boolean status, final String errorMessage, final String parameterName) {
        this.status = status;
        this.errorMessage = errorMessage;
        this.parameterName = parameterName.concat(" parameter is missing.");
        this.errors = null;
        this.functionalErrorCode = null;
    }

    public List<String> getErrors() {
        return errors;
    }

    public boolean isStatus() {
        return status;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public FunctionalErrorCode getFunctionalErrorCode() {
        return functionalErrorCode;
    }

    public String getParameterName() {
        return parameterName;
    }
}