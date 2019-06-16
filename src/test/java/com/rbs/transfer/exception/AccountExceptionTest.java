package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class AccountExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_AccountException() throws AccountException {
        expectedException.expect(AccountException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.COULD_NOT_TRANSFER
        .toString()));
        this.thrower.throw_AccountException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_AccountException_with_error_message() throws AccountException {
        expectedException.expect(AccountException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_AccountException_with_error_message();
    }
}
