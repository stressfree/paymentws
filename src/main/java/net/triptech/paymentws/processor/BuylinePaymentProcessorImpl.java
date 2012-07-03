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

import webpay.client.Webpay;

import java.io.IOException;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

/**
 * The Class BuylinePaymentProcessorImpl.
 */
public class BuylinePaymentProcessorImpl implements PaymentProcessor {

    /** The payment logger. */
    private static Logger logger = Logger.getLogger(BuylinePaymentProcessorImpl.class);

    /** The client id. */
    private String clientId;

    /** The certificate path. */
    private String certificatePath;

    /** The certificate passphrase. */
    private String certificatePassphrase;

    /** The servers. */
    private List<String> servers;

    /** The port. */
    private int port;

    /** The debug mode. */
    private boolean debugMode;

    /** The number of transaction attempts. */
    private static final int ATTEMPTS = 3;

    /** The wait time between attempts. */
    private static final int WAIT = 500;

    /**
     * Sets the client id.
     *
     * @param clientIdVal the new client id
     */
    public final void setClientId(final String clientIdVal) {
        this.clientId = clientIdVal;
    }

    /**
     * Sets the certificate path.
     *
     * @param certificatePathVal the new certificate path
     */
    public final void setCertificatePath(final String certificatePathVal) {
        this.certificatePath = certificatePathVal;
    }

    /**
     * Sets the certificate passphrase.
     *
     * @param certificatePassphraseVal the new certificate passphrase
     */
    public final void setCertificatePassphrase(final String certificatePassphraseVal) {
        this.certificatePassphrase = certificatePassphraseVal;
    }

    /**
     * Sets the servers.
     *
     * @param serverList the new servers
     */
    public final void setServers(final List<String> serverList) {
        this.servers = serverList;
    }

    /**
     * Sets the server port.
     *
     * @param serverPort the new server port
     */
    public final void setServerPort(final int serverPort) {
        this.port = serverPort;
    }

    /**
     * Sets the debug mode flag.
     *
     * @param debugVal the new debug mode
     */
    public final void setDebugMode(final boolean debugVal) {
        this.debugMode = debugVal;
    }

    /**
     * Process a payment based on the supplied payment details.
     *
     * @param details the payment details
     * @return the processed payment
     * @throws SFSPaymentException the SFS payment exception
     */
    public final ProcessedPayment process(final PaymentDetails details)
            throws PaymentException {

        // Validate the supplied details and configuration
        validate(details);

        // The processed payment details to return
        ProcessedPayment processedPayment = new ProcessedPayment();

        // The webpay client
        Webpay webpay = null;
        String responseCode = null;

        try {
            webpay = new Webpay(this.clientId , this.certificatePath,
                    this.certificatePassphrase);
        } catch (Exception e) {
            logger.error("Error instantiating the Webpay object", e);
            throw new PaymentException("Error instantiating the Webpay object: "
                    + e.getMessage());
        }

        // Set the servers and port
        String[] serverArray = new String[this.servers.size()];
        for (int i = 0; i < this.servers.size(); i++) {
            serverArray[i] = this.servers.get(i);
        }
        webpay.setServers(serverArray);
        webpay.setPort(this.port);

        String value = String.format("%.2f", details.getPurchaseTotalValue());
        logger.info("Credit card transaction value: " + value);

        // Populate the Webpay object with required data
        webpay.put("TRANSACTIONTYPE", "PURCHASE");
        webpay.put("INTERFACE", "CREDITCARD");
        webpay.put("CARDEXPIRYDATE", details.getCardExpiryDate());
        webpay.put("CARDDATA", details.getCardNumber());
        webpay.put("TOTALAMOUNT", value);

        // Optional data
        if (StringUtils.isNotBlank(details.getCardHolderName())) {
            webpay.put("MERCHANT_CARDHOLDERNAME", details.getCardHolderName());
        }
        if (details.getCardSecurityCode() > 0) {
            webpay.put("CVC2", String.valueOf(details.getCardSecurityCode()));
            webpay.put("CCI", "1");
        }
        if (details.getPurchaseTaxValue() > 0) {
            String taxValue = String.format("%.2f", details.getPurchaseTaxValue());
            logger.info("Credit card transaction tax value: " + taxValue);
            webpay.put("TAXAMOUNT", taxValue);
        }
        if (StringUtils.isNotBlank(details.getClientReferenceNumber())) {
            webpay.put("CLIENTREF", details.getClientReferenceNumber());
        }
        if (StringUtils.isNotBlank(details.getPurchaseDescription())) {
            webpay.put("MERCHANT_DESCRIPTION", details.getPurchaseDescription());
        }
        if (StringUtils.isNotBlank(details.getPurchaseComment())) {
            webpay.put("COMMENT", details.getPurchaseComment());
        }

        // Execute the transaction.
        try {
            if (!this.debugMode) {
                webpay.execute();
            } else {
                responseCode = "DEBUG";
            }
        } catch (IOException ioe) {
            // The transaction has failed at a protocol level.
            logger.error("Error executing transaction.", ioe);
            // If TXNREFERENCE has been set, then the transaction was initialised before
            // failing. Let the transaction continue to see if it brings a result back.
            if (webpay.get("TXNREFERENCE") != null) {
                responseCode = "IP";
            }
        }

        // Check to see if the transaction is still "IN PROGRESS".
        // If it is, poll server with status requests until either the response
        // code changes or to a maximum of three status requests.
        if (responseCode == null) {
            responseCode = webpay.get("RESPONSECODE");
        }
        for (int statusCheckCount = 0; statusCheckCount < ATTEMPTS; statusCheckCount++) {
            if (StringUtils.equals("IP", responseCode)) {
                // The transaction is still in progress. Send a status request.
                webpay.put("TRANSACTIONTYPE", "STATUS");
                try {
                    Thread.sleep(WAIT);
                    // Half-second polling delay
                    webpay.execute();
                } catch (IOException ioe) {
                    // The transaction has failed at a protocol level.
                    logger.error("Error executing transaction.", ioe);
                    throw new PaymentException("Error executing transaction: "
                            + ioe.getMessage());
                } catch (InterruptedException ie) {
                    // The thread was interrupted during processing.
                    logger.error("Error executing transaction (thread interrupted).", ie);
                    throw new PaymentException("Error executing transaction: "
                            + ie.getMessage());
                }
                responseCode = webpay.get("RESPONSECODE");
            } else {
                // Break out of for loop, we don't need poll anymore as the
                // transaction is no longer IN PROGRESS
                statusCheckCount = ATTEMPTS;
            }
        }
        if (StringUtils.equals("IP", responseCode)) {
            // The transaction was still in progress after three iterations through
            // the polling loop. Kill the session.
            logger.error("The Webpay server failed to respond in a timely fashion.");
            throw new PaymentException("The Webpay server failed to respond in a "
                    + "timely fashion. Try sending transaction again.");
        } else {
            // The transaction has completed. Populate the processedPayment bean.
            if (debugMode) {
                processedPayment.setSuccess(true);
                processedPayment.setReference("TEST");
            } else {
                logger.debug("Response Code: " + webpay.get("RESPONSECODE"));
                logger.debug("Response Text: " + webpay.get("RESPONSETEXT"));
                logger.debug("Authorisation Code: " + webpay.get("AUTHCODE"));
                logger.debug("Pre Auth Number: " + webpay.get("PREAUTHNUMBER"));
                logger.debug("Error Message: " + webpay.get("ERROR"));

                responseCode = webpay.get("RESPONSECODE");
                if (StringUtils.equals(responseCode, "00")
                        || StringUtils.equals(responseCode, "08")) {
                    processedPayment.setSuccess(true);
                }
                processedPayment.setReference(webpay.get("TXNREFERENCE"));
                processedPayment.setMessage(webpay.get("RESPONSETEXT"));
                processedPayment.setLogMessage(webpay.get("ERROR"));
            }
        }
        return processedPayment;
    }

    /**
     * Validate the supplied payment details.
     *
     * @param details the details
     * @throws SFSPaymentException the sFS payment exception
     */
    private void validate(final PaymentDetails details) throws PaymentException {

        if (details == null) {
            throw new PaymentException("Payment details cannot be null");
        }
        if (!details.isCardValid()) {
            throw new PaymentException("A valid credit card number is required");
        }
        final double minimumValue = 0.01;
        if (details.getPurchaseTotalValue() < minimumValue) {
            throw new PaymentException("No value specified for credit card payment");
        }
        if (details.getPurchaseTaxValue() > details.getPurchaseTotalValue()) {
            throw new PaymentException("The tax value cannot be greater than "
                    + "the total purchase value");
        }
        if (StringUtils.isBlank(this.clientId)) {
            throw new PaymentException("A client ID is required");
        }
        if (StringUtils.isBlank(this.certificatePath)) {
            throw new PaymentException("A security certificate path is required");
        }
        if (StringUtils.isBlank(this.certificatePassphrase)) {
            throw new PaymentException("A security passphrase is required");
        }
        if (this.servers == null || this.servers.size() == 0) {
            throw new PaymentException("At least one Buyline server is required");
        }
        if (this.port < 1) {
            throw new PaymentException("A valid Buyline server port is required");
        }
    }
}
