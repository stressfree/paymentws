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
package net.triptech.paymentws.processor;

/**
 * The Interface PaymentProcessor.
 */
public interface PaymentProcessor {

    /**
     * Process a payment based on the supplied payment details.
     *
     * @param details the payment details
     * @return the processed payment
     * @throws SFSPaymentException the SFS payment exception
     */
    ProcessedPayment process(final PaymentDetails details) throws PaymentException;

}
