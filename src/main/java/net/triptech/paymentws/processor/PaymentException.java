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
 * The Class PaymentException.
 *
 * @author David Harrison
 */
public class PaymentException extends Exception {

    /** The unique serial version UID for the class. */
    private static final long serialVersionUID = 2003912120995945563L;

    /**
     * Constructs an instance of <code>PaymentException</code>
     * with the specified detail message.
     *
     * @param msg the detail message.
     */
    public PaymentException(final String msg) {
        super(msg);
    }
}
