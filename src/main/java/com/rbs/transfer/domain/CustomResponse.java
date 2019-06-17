package com.rbs.transfer.domain;

import com.rbs.transfer.utils.FunctionalErrorCode;

import java.util.Collections;
import java.util.List;

public class CustomResponse {
    private final boolean isCompleted;
    private final boolean isSuccess;
    private final List<String> errors;

    private CustomResponse(final boolean isCompleted, final boolean isSuccess, final List<String> errors) {
        this.isCompleted = isCompleted;
        this.isSuccess = isSuccess;
        this.errors = errors == null ? Collections.emptyList() : errors;
    }

    public boolean isCompleted() {
        return this.isCompleted;
    }

    public boolean isSuccess() {
        return isCompleted && isSuccess;
    }

    public boolean isFailed() {
        return isCompleted && !isSuccess;
    }

    public List<String> getErrors() {
        return errors;
    }


    public static CustomResponse succeed() {
        return new CustomResponse(true, true, Collections.emptyList());
    }

    public static CustomResponse failed(final String message) {
        return new CustomResponse(true, false, Collections.singletonList(message));
    }

    public static CustomResponse failed(final FunctionalErrorCode functionalError) {
        return new CustomResponse(true, false, Collections.singletonList(functionalError.toString()));
    }

    public static CustomResponse failed(final List<String> errors) {
        return new CustomResponse(true, false, errors);
    }

    public static CustomResponse notCompleted() {
        return new CustomResponse(false, false, Collections.emptyList());
    }
}
