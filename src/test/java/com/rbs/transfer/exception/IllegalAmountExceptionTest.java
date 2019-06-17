package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class IllegalAmountExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_IllegalAmountException() throws IllegalAmountException {
        expectedException.expect(IllegalAmountException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Illegal Amount"));
        this.thrower.throw_IllegalAmountException();
    }

    @Test
    public void should_throw_IllegalAmountException_with_func_error_code() throws IllegalAmountException {
        expectedException.expect(IllegalAmountException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.AMOUNT_SHOULD_BE_BIGGER_THEN_ZERO.toString()));
        this.thrower.throw_IllegalAmountException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_IllegalAmountException_with_error_message() throws IllegalAmountException {
        expectedException.expect(IllegalAmountException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_IllegalAmountException_with_error_message();
    }
}