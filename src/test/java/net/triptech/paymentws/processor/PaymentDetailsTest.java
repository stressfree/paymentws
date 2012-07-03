package net.triptech.paymentws.processor;

import static org.junit.Assert.assertEquals;
import org.junit.Test;

/**
 * The Class PaymentDetailsTest.
 */
public class PaymentDetailsTest {

    /**
     * Test get card holder name.
     */
    @Test
    public final void testGetCardHolderName() {

        String expResult = "Test Person";

        PaymentDetails instance = new PaymentDetails();
        instance.setCardHolderName(expResult);

        assertEquals(expResult, instance.getCardHolderName());
    }

    /**
     * Test get card number.
     */
    @Test
    public final void testGetCardNumber() {

        String expResult = "5933739501088633";

        PaymentDetails instance = new PaymentDetails();
        instance.setCardNumber(expResult);

        assertEquals(expResult, instance.getCardNumber());
    }

    /**
     * Test of the is card valid function.
     */
    @Test
    public final void testIsCardValid() {

        PaymentDetails instance = new PaymentDetails();

        instance.setCardNumber("123456789101112");
        assertEquals(true, instance.isCardValid());

        instance.setCardNumber("123456789");
        assertEquals(false, instance.isCardValid());

        instance.setCardNumber("123456789123456789");
        assertEquals(false, instance.isCardValid());

        instance.setCardNumber("123ABC456789D");
        assertEquals(false, instance.isCardValid());
    }


    /**
     * Test get card expiry month.
     */
    @Test
    public final void testGetCardExpiryMonth() {

        int expResult = 1;

        PaymentDetails instance = new PaymentDetails();
        instance.setCardExpiryMonth(expResult);

        assertEquals(expResult, instance.getCardExpiryMonth());
    }

    /**
     * Test get card expiry year.
     */
    @Test
    public final void testGetCardExpiryYear() {

        int expResult = 10;

        PaymentDetails instance = new PaymentDetails();
        instance.setCardExpiryYear(expResult);

        assertEquals(expResult, instance.getCardExpiryYear());
    }

    /**
     * Test get card expiry date.
     */
    @Test
    public final void testGetCardExpiryDate() {

        String expResult1 = "1013";
        String expResult2 = "0113";
        String expResult3 = "0101";

        PaymentDetails instance = new PaymentDetails();
        instance.setCardExpiryMonth(10);
        instance.setCardExpiryYear(13);

        assertEquals(expResult1, instance.getCardExpiryDate());

        instance.setCardExpiryMonth(1);

        assertEquals(expResult2, instance.getCardExpiryDate());

        instance.setCardExpiryYear(1);

        assertEquals(expResult3, instance.getCardExpiryDate());

        instance.setCardExpiryMonth(10);
        instance.setCardExpiryYear(2013);

        assertEquals(expResult1, instance.getCardExpiryDate());
    }

    /**
     * Test get card security code.
     */
    @Test
    public final void testGetCardSecurityCode() {

        int expResult = 555;

        PaymentDetails instance = new PaymentDetails();
        instance.setCardSecurityCode(expResult);

        assertEquals(expResult, instance.getCardSecurityCode());
    }

    /**
     * Test get client reference number.
     */
    @Test
    public final void testGetClientReferenceNumber() {

        String expResult = "Test";

        PaymentDetails instance = new PaymentDetails();
        instance.setClientReferenceNumber(expResult);

        assertEquals(expResult, instance.getClientReferenceNumber());
    }

    /**
     * Test get purchase description.
     */
    @Test
    public final void testGetPurchaseDescription() {

        String expResult = "Test purchase description";

        PaymentDetails instance = new PaymentDetails();
        instance.setPurchaseDescription(expResult);

        assertEquals(expResult, instance.getPurchaseDescription());
    }

    /**
     * Test get purchase comment.
     */
    @Test
    public final void testGetPurchaseComment() {

        String expResult = "Test purchase comment";

        PaymentDetails instance = new PaymentDetails();
        instance.setPurchaseComment(expResult);

        assertEquals(expResult, instance.getPurchaseComment());
    }

    /**
     * Test get purchase total value.
     */
    @Test
    public final void testGetPurchaseTotalValue() {

        double expResult = 100.00;

        PaymentDetails instance = new PaymentDetails();
        instance.setPurchaseTotalValue(expResult);

        boolean success = false;
        if (expResult == instance.getPurchaseTotalValue()) {
            success = true;
        }
        assertEquals(true, success);
    }

    /**
     * Test get purchase tax value.
     */
    @Test
    public final void testGetPurchaseTaxValue() {

        double expResult = 33.00;

        PaymentDetails instance = new PaymentDetails();
        instance.setPurchaseTaxValue(expResult);

        boolean success = false;
        if (expResult == instance.getPurchaseTaxValue()) {
            success = true;
        }
        assertEquals(true, success);
    }

}
