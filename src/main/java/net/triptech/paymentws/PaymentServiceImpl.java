/*******************************************************************************
 * Copyright (c) 2012 David Harrison.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Public License v3.0
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/gpl-3.0.html
 *
 * Contributors:
 *     David Harrison - initial API and implementation
 ******************************************************************************/
package net.triptech.paymentws;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import net.triptech.paymentws.processor.PaymentDetails;
import net.triptech.paymentws.processor.PaymentException;
import net.triptech.paymentws.processor.PaymentProcessor;
import net.triptech.paymentws.processor.ProcessedPayment;

/**
 * The Class PaymentServiceImpl.
 *
 * @author David Harrison
 */
public class PaymentServiceImpl {

    /** The payment logger. */
    private static Logger logger = Logger.getLogger(PaymentServiceImpl.class);

    /** The payment processor. */
    @Resource
    private PaymentProcessor paymentProcessor;

    /** The access list. */
    private Map<String, String> accessList;


    /**
     * Sets the access list.
     *
     * @param accessListMap the access list
     */
    public final void setAccessList(final Map<String, String> accessListMap) {
        this.accessList = accessListMap;
    }

    /**
     * Process the transaction.
     *
     * @param accessId the access id
     * @param secret the secret
     * @param cardNumber the card number
     * @param cardHolder the card holder
     * @param expiryMonth the expiry month
     * @param expiryYear the expiry year
     * @param securityCode the security code
     * @param totalValue the total value
     * @param taxValue the tax value
     * @param clientReference the client reference
     * @param description the description
     * @param purchaseComment the purchase comment
     * @return the result string
     */
    public final String process(final String accessId, final String secret,
            final String cardNumber, final String cardHolder,
            final int expiryMonth, final int expiryYear, final int securityCode,
            final double totalValue, final double taxValue, final String clientReference,
            final String description, final String purchaseComment) {

        boolean allowed = false;

        ProcessedPayment payment = new ProcessedPayment();

        if (this.accessList != null) {
            for (String keyVal : this.accessList.keySet()) {
                String secretVal = this.accessList.get(keyVal);

                if (StringUtils.equals(keyVal,  accessId)
                        && StringUtils.equals(secretVal,  secret)) {
                    allowed = true;
                }
            }
        } else {
            // No keys defined, set to allow
            allowed = true;
        }

        if (!allowed) {
            payment.setSuccess(false);
            payment.setMessage("Access denied");
        } else {
            PaymentDetails details = new PaymentDetails();

            details.setCardNumber(cardNumber);
            details.setCardHolderName(cardHolder);
            details.setCardExpiryMonth(expiryMonth);
            details.setCardExpiryYear(expiryYear);
            details.setCardSecurityCode(securityCode);

            details.setPurchaseTotalValue(totalValue);
            details.setPurchaseTaxValue(taxValue);

            details.setClientReferenceNumber(clientReference);
            details.setPurchaseDescription(description);
            details.setPurchaseComment(purchaseComment);

            try {
                payment = this.paymentProcessor.process(details);
            } catch (PaymentException pe) {
                logger.error("Error processing payment: " + pe.getMessage());
                payment.setSuccess(false);
                payment.setMessage("Error processing payment");
                payment.setLogMessage(pe.getMessage());
            }
        }

        String result = "error";
        if (payment.getSuccess()) {
            result = "success";
        }

        return "'" + result + "','" + payment.getReference() + "','"
                + payment.getMessage() + "','" + payment.getLogMessage() + "'";
    }
}
