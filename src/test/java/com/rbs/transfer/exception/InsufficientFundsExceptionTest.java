package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class InsufficientFundsExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_InsufficientFundsException() throws InsufficientFundsException {
        expectedException.expect(InsufficientFundsException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Insufficient funds"));
        this.thrower.throw_InsufficientFundsException();
    }

    @Test
    public void should_throw_InsufficientFundsException_with_func_error_code() throws InsufficientFundsException {
        expectedException.expect(InsufficientFundsException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.SOURCE_ACCOUNT_DOES_NOT_HAS_ENOUGH_BALANCE.toString()));
        this.thrower.throw_InsufficientFundsException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_InsufficientFundsException_with_error_message() throws InsufficientFundsException {
        expectedException.expect(InsufficientFundsException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_InsufficientFundsException_with_error_message();
    }
}