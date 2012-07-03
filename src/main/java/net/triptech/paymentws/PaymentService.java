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

/**
 * The Class PaymentService.
 *
 * @author David Harrison
 */
public interface PaymentService {

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
    String process(final String accessId, final String secret,
            final String cardNumber, final String cardHolder,
            final int expiryMonth, final int expiryYear, final int securityCode,
            final double totalValue, final double taxValue, final String clientReference,
            final String description, final String purchaseComment);

}
