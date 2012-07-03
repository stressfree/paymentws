package net.triptech.paymentws.processor;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.assertEquals;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
        "/spring-context.xml"
})
public class BuylinePaymentProcessorImplTest {

    @Resource BuylinePaymentProcessorImpl buylinePaymentProcessor;

    @Test
    public final void testProcess() throws PaymentException {

        PaymentDetails details = new PaymentDetails();
        details.setCardNumber("4564456445644564");
        details.setCardExpiryMonth(4);
        details.setCardExpiryYear(12);
        details.setCardHolderName("Test Person");
        details.setPurchaseTotalValue(100);
        details.setPurchaseTaxValue(20);
        details.setClientReferenceNumber("ClientId");
        details.setPurchaseDescription("A test description");
        details.setPurchaseComment("A test comment");

        ProcessedPayment result = this.buylinePaymentProcessor.process(details);

        System.out.println(" - Success: " + result.getSuccess());
        System.out.println(" - Transaction reference: " + result.getReference());
        System.out.println(" - Message: " + result.getMessage());
        System.out.println(" - Log message: " + result.getLogMessage());

        assertEquals(true, result.getSuccess());
    }

}
