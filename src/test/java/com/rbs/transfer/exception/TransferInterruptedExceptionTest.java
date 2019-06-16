package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransferInterruptedExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_TransferInterruptedException() throws TransferInterruptedException {
        expectedException.expect(TransferInterruptedException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Transfer process interrupted"));
        this.thrower.throw_TransferInterruptedException();
    }

    @Test
    public void should_throw_TransferInterruptedException_with_func_error_code() throws TransferInterruptedException {
        expectedException.expect(TransferInterruptedException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.DESTINATION_ACCOUNT_NOT_FOUND.toString()));
        this.thrower.throw_TransferInterruptedException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_TransferInterruptedException_with_error_message() throws TransferInterruptedException {
        expectedException.expect(TransferInterruptedException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_TransferInterruptedException_with_error_message();
    }
}