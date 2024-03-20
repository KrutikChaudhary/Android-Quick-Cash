package com.example.group12.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The CreditCardValidator class provides methods to validate credit card information.
 */
public class CreditCardValidator {

    public static final int CARDLENGTHFLOOR = 13; // Minimum length of a credit card number
    public static final int CARDLENGTHCEILING = 16; // Maximum length of a credit card number
    public static final int CVVLENGTH = 3; // Length of CVV number

    /**
     * Checks if the CVV number is empty.
     *
     * @param cvv The CVV number to be checked.
     * @return true if the CVV number is empty, false otherwise.
     */
    public boolean isCVVEmpty(String cvv){
        return cvv.isEmpty();
    }

    /**
     * Checks if the credit card number is empty.
     *
     * @param num The credit card number to be checked.
     * @return true if the credit card number is empty, false otherwise.
     */
    public boolean isCardNumEmpty(String num){
        return num.isEmpty();
    }

    /**
     * Checks if the expiry date is empty.
     *
     * @param expiry The expiry date to be checked.
     * @return true if the expiry date is empty, false otherwise.
     */
    public boolean isExpiryEmpty(String expiry){
        return expiry.isEmpty();
    }

    /**
     * Checks if the credit card number is valid based on a regex pattern.
     *
     * @param cardNumber The credit card number to be validated.
     * @return true if the credit card number is valid, false otherwise.
     */
    public boolean isValidCardNum(String cardNumber) {
        String cardNum_format = ""; // Regular expression pattern for validating card numbers
        Pattern cardNum_pattern = Pattern.compile(cardNum_format);
        Matcher matcher = cardNum_pattern.matcher(cardNumber);
        return matcher.matches();
    }

    /**
     * Checks if the CVV number is valid based on a regex pattern.
     *
     * @param cvv The CVV number to be validated.
     * @return true if the CVV number is valid, false otherwise.
     */
    public boolean isValidCVV(String cvv) {
        String cvv_format = ""; // Regular expression pattern for validating CVV numbers
        Pattern cvv_pattern = Pattern.compile(cvv_format);
        Matcher matcher = cvv_pattern.matcher(cvv);
        return matcher.matches();
    }

    /**
     * Checks if the expiry date is valid based on a regex pattern.
     *
     * @param expiry The expiry date to be validated.
     * @return true if the expiry date is valid, false otherwise.
     */
    public boolean isValidExpiry(String expiry) {
        String expiry_format = ""; // Regular expression pattern for validating expiry dates
        Pattern expiry_pattern = Pattern.compile(expiry_format);
        Matcher matcher = expiry_pattern.matcher(expiry);
        return matcher.matches();
    }

    /**
     * Checks if the length of the credit card number is within the acceptable range.
     *
     * @param cardNumber The credit card number to be checked.
     * @return true if the length of the credit card number is valid, false otherwise.
     */
    public boolean checkCardLength(String cardNumber){
        return cardNumber.length() == CARDLENGTHFLOOR || cardNumber.length() == CARDLENGTHCEILING;
    }

    /**
     * Checks if the length of the CVV number is valid.
     *
     * @param cvv The CVV number to be checked.
     * @return true if the length of the CVV number is valid, false otherwise.
     */
    public boolean checkCVVLength(String cvv){
        return cvv.length() == CVVLENGTH;
    }
}
