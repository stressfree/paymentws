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
 * The Class ProcessedPayment.
 *
 * @author David Harrison 18th May 2011
 */
public class ProcessedPayment {

    /** The reference. */
    private String reference;

    /** The success. */
    private boolean success = false;

    /** The message. */
    private String message;

    /** The log message. */
    private String logMessage;


    /**
     * Sets the reference.
     *
     * @param referenceVal the new reference value
     */
    public final void setReference(final String referenceVal) {
        this.reference = referenceVal;
    }

    /**
     * Gets the reference number.
     *
     * @return the reference number
     */
    public final String getReference() {
        if (this.reference == null) {
            this.reference = "";
        }
        return this.reference.trim();
    }

    /**
     * Sets the success.
     *
     * @param successVal the new success
     */
    public final void setSuccess(final boolean successVal) {
        this.success = successVal;
    }

    /**
     * Gets the success.
     *
     * @return the success
     */
    public final boolean getSuccess() {
        return this.success;
    }

    /**
     * Sets the message.
     *
     * @param messageVal the new message
     */
    public final void setMessage(final String messageVal) {
        this.message = messageVal;
    }

    /**
     * Gets the message.
     *
     * @return the message
     */
    public final String getMessage() {
        if (this.message == null) {
            this.message = "";
        }
        return this.message.trim();
    }

    /**
     * Sets the log message.
     *
     * @param logMessageVal the new log message
     */
    public final void setLogMessage(final String logMessageVal) {
        this.logMessage = logMessageVal;
    }

    /**
     * Gets the log message.
     *
     * @return the log message
     */
    public final String getLogMessage() {
        if (this.logMessage == null) {
            this.logMessage = "";
        }
        return this.logMessage;
    }
}
