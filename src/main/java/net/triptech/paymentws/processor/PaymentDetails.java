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

import org.apache.commons.lang.StringUtils;

/**
 * The Class PaymentDetails.
 *
 * @author David Harrison 18th May 2011
 */
public class PaymentDetails {

    /** The card holder name. */
    private String cardHolderName;

    /** The card number. */
    private String cardNumber;

    /** The card expiry month. */
    private int cardExpiryMonth;

    /** The card expiry year. */
    private int cardExpiryYear;

    /** The card security code. */
    private int cardSecurityCode;

    /** The client reference number. */
    private String clientReferenceNumber;

    /** The purchase description. */
    private String purchaseDescription;

    /** The purchase comment. */
    private String purchaseComment;

    /** The purchase total value. */
    private double purchaseTotalValue;

    /** The purchase tax value. */
    private double purchaseTaxValue;

    /**
     * Gets the card holder name.
     *
     * @return the card holder name
     */
    public final String getCardHolderName() {
        if (this.cardHolderName == null) {
            this.cardHolderName = "";
        }
        final int length = 50;
        if (this.cardHolderName.length() > length) {
            this.cardHolderName = this.cardHolderName.substring(0, length);
        }
        return cardHolderName.trim();
    }

    /**
     * Sets the card holder name.
     *
     * @param cardHolderNameVal the new card holder name
     */
    public final void setCardHolderName(final String cardHolderNameVal) {
        this.cardHolderName = cardHolderNameVal;
    }

    /**
     * Gets the card number.
     *
     * @return the card number
     */
    public final String getCardNumber() {
        if (this.cardNumber == null) {
            this.cardNumber = "";
        }
        return this.cardNumber;
    }

    /**
     * Sets the card number.
     *
     * @param cardNumberVal the new card number
     */
    public final void setCardNumber(final String cardNumberVal) {
        this.cardNumber = StringUtils.replace(cardNumberVal, " ", "").trim();
    }

    /**
     * Test whether the supplied credit card number is valid.
     *
     * @return true, if successful
     */
    public final boolean isCardValid() {

        boolean valid = false;

        String cardNo = this.getCardNumber();

        final int min = 11;
        final int max = 17;
        if (cardNo.length() > min && cardNo.length() < max
                && containsOnlyNumbers(cardNo)) {
            valid = true;
        }
        return valid;
    }

    /**
     * Gets the card expiry month.
     *
     * @return the card expiry month
     */
    public final int getCardExpiryMonth() {
        return cardExpiryMonth;
    }

    /**
     * Sets the card expiry month.
     *
     * @param cardExpiryMonthVal the new card expiry month
     */
    public final void setCardExpiryMonth(final int cardExpiryMonthVal) {
        this.cardExpiryMonth = cardExpiryMonthVal;
    }

    /**
     * Gets the card expiry year.
     *
     * @return the card expiry year
     */
    public final int getCardExpiryYear() {
        return cardExpiryYear;
    }

    /**
     * Sets the card expiry year.
     *
     * @param cardExpiryYearVal the new card expiry year
     */
    public final void setCardExpiryYear(final int cardExpiryYearVal) {
        this.cardExpiryYear = cardExpiryYearVal;
    }

    /**
     * Gets the card expiry date.
     *
     * @return the card expiry date
     */
    public final String getCardExpiryDate() {
        StringBuffer expiryDate = new StringBuffer();

        String month = String.valueOf(this.cardExpiryMonth);
        String year = String.valueOf(this.cardExpiryYear);

        if (month.length() == 1) {
            expiryDate.append("0");
        }
        if (month.length() > 2) {
            expiryDate.append(month.substring(month.length() - 2));
        } else {
            expiryDate.append(month);
        }

        if (year.length() == 1) {
            expiryDate.append("0");
        }
        if (year.length() > 2) {
            expiryDate.append(year.substring(year.length() - 2));
        } else {
            expiryDate.append(year);
        }
        return expiryDate.toString();
    }

    /**
     * Gets the card security code.
     *
     * @return the card security code
     */
    public final int getCardSecurityCode() {
        return cardSecurityCode;
    }

    /**
     * Sets the card security code.
     *
     * @param cardSecurityCodeVal the new card security code
     */
    public final void setCardSecurityCode(final int cardSecurityCodeVal) {
        this.cardSecurityCode = cardSecurityCodeVal;
    }

    /**
     * Gets the client reference number.
     *
     * @return the client reference number
     */
    public final String getClientReferenceNumber() {
        if (this.clientReferenceNumber == null) {
            this.clientReferenceNumber = "";
        }
        final int length = 50;
        if (this.clientReferenceNumber.length() > length) {
            this.clientReferenceNumber = this.clientReferenceNumber.substring(0, length);
        }
        return clientReferenceNumber;
    }

    /**
     * Sets the client reference number.
     *
     * @param clientReferenceNumberVal the new client reference number
     */
    public final void setClientReferenceNumber(final String clientReferenceNumberVal) {
        this.clientReferenceNumber = clientReferenceNumberVal;
    }

    /**
     * Gets the purchase description.
     *
     * @return the purchase description
     */
    public final String getPurchaseDescription() {
        if (this.purchaseDescription == null) {
            this.purchaseDescription = "";
        }
        final int length = 100;
        if (this.purchaseDescription.length() > length) {
            this.purchaseDescription = this.purchaseDescription.substring(0, length);
        }
        return purchaseDescription;
    }

    /**
     * Sets the purchase description.
     *
     * @param purchaseDescriptionVal the new purchase description
     */
    public final void setPurchaseDescription(final String purchaseDescriptionVal) {
        this.purchaseDescription = purchaseDescriptionVal;
    }

    /**
     * Gets the purchase comment.
     *
     * @return the purchase comment
     */
    public final String getPurchaseComment() {
        if (this.purchaseComment == null) {
            this.purchaseComment = "";
        }
        final int length = 255;
        if (this.purchaseComment.length() > length) {
            this.purchaseComment = this.purchaseComment.substring(0, length);
        }
        return purchaseComment;
    }

    /**
     * Sets the purchase comment.
     *
     * @param purchaseCommentVal the new purchase comment
     */
    public final void setPurchaseComment(final String purchaseCommentVal) {
        this.purchaseComment = purchaseCommentVal;
    }

    /**
     * Gets the purchase total value.
     *
     * @return the purchase total value
     */
    public final double getPurchaseTotalValue() {
        return purchaseTotalValue;
    }

    /**
     * Sets the purchase total value.
     *
     * @param purchaseTotalVal the new purchase total value
     */
    public final void setPurchaseTotalValue(final double purchaseTotalVal) {
        this.purchaseTotalValue = purchaseTotalVal;
    }

    /**
     * Gets the purchase tax value.
     *
     * @return the purchase tax value
     */
    public final double getPurchaseTaxValue() {
        return purchaseTaxValue;
    }

    /**
     * Sets the purchase tax value.
     *
     * @param purchaseTaxVal the new purchase tax value
     */
    public final void setPurchaseTaxValue(final double purchaseTaxVal) {
        this.purchaseTaxValue = purchaseTaxVal;
    }

    /**
     * Contains only numbers test.
     *
     * @param str the string to test
     * @return true, if successful
     */
    private boolean containsOnlyNumbers(final String str) {

        boolean containsOnlyNumbers = true;

        if (StringUtils.isNotBlank(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (!Character.isDigit(str.charAt(i))) {
                    containsOnlyNumbers = false;
                }
            }
        } else {
            containsOnlyNumbers = false;
        }
        return containsOnlyNumbers;
    }

}
