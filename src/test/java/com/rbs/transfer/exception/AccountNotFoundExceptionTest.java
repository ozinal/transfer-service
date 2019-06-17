package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountNotFoundExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_AccountNotFoundException() throws AccountNotFoundException {
        expectedException.expect(AccountNotFoundException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Account not found."));
        this.thrower.throw_AccountNotFoundException();
    }

    @Test
    public void should_throw_AccountNotFoundException_with_func_error_code() throws AccountNotFoundException {
        expectedException.expect(AccountNotFoundException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.AMOUNT_CAN_NOT_BE_NULL.toString()));
        this.thrower.throw_AccountNotFoundException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_AccountNotFoundException_with_error_message() throws AccountNotFoundException {
        expectedException.expect(AccountNotFoundException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_AccountNotFoundException_with_error_message();
    }
}