package net.triptech.paymentws.processor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * The Class ProcessedPaymentTest.
 */
public class ProcessedPaymentTest {

    /**
     * Test get reference.
     */
    @Test
    public final void testGetId() {

        String expResult = "ABC123";

        ProcessedPayment instance = new ProcessedPayment();
        instance.setReference(expResult);

        assertEquals(expResult, instance.getReference());
    }

    /**
     * Test get success.
     */
    @Test
    public final void testGetSuccess() {

        boolean expResult = true;

        ProcessedPayment instance = new ProcessedPayment();
        instance.setSuccess(expResult);

        assertEquals(expResult, instance.getSuccess());
    }

    /**
     * Test get message.
     */
    @Test
    public final void testGetMessage() {

        String expResult = "Success";

        ProcessedPayment instance = new ProcessedPayment();
        instance.setMessage(expResult);

        assertEquals(expResult, instance.getMessage());
    }

    /**
     * Test get log message.
     */
    @Test
    public final void testGetLogMessage() {

        String expResult = "Test log message";

        ProcessedPayment instance = new ProcessedPayment();
        instance.setLogMessage(expResult);

        assertEquals(expResult, instance.getLogMessage());
    }

}
