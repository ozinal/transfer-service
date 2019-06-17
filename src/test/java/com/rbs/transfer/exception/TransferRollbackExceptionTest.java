package com.rbs.transfer.exception;

import com.rbs.transfer.utils.FunctionalErrorCode;
import com.rbs.util.Thrower;
import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TransferRollbackExceptionTest {

    private Thrower thrower;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        this.thrower = new Thrower();
    }

    @Test
    public void should_throw_TransferRollbackException() throws TransferRollbackException {
        expectedException.expect(TransferRollbackException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Could not rollback transfer"));
        this.thrower.throw_TransferRollbackException();
    }

    @Test
    public void should_throw_TransferRollbackException_with_func_error_code() throws TransferRollbackException {
        expectedException.expect(TransferRollbackException.class);
        expectedException.expectMessage(CoreMatchers.equalTo(FunctionalErrorCode.COULD_NOT_TRANSFER.toString()));
        this.thrower.throw_TransferRollbackException_with_FunctionalErrorCode();
    }

    @Test
    public void should_throw_TransferInterruptedException_with_error_message() throws TransferRollbackException {
        expectedException.expect(TransferRollbackException.class);
        expectedException.expectMessage(CoreMatchers.equalTo("Something went wrong!"));
        this.thrower.throw_TransferRollbackException_with_error_message();
    }
}