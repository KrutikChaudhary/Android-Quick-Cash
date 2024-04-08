package com.example.group12.logic.validator.card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * The CreditCardValidator class provides methods to validate credit card information.
 */
public class CreditCardValidator {

    public static final int CARDLENGTHFLOOR = 13; // Minimum length of a credit card number
    public static final int CARDLENGTHCEILING = 16; // Maximum length of a credit card number


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
        String cardNumFormat = ""; // Regular expression pattern for validating card numbers
        Pattern cardNumPattern = Pattern.compile(cardNumFormat);
        Matcher matcher = cardNumPattern.matcher(cardNumber);
        return matcher.matches();
    }


    /**
     * Checks if the expiry date is valid based on a regex pattern.
     *
     * @param expiry The expiry date to be validated.
     * @return true if the expiry date is valid, false otherwise.
     */
    public boolean isValidExpiry(String expiry) {
        String expiryFormat = ""; // Regular expression pattern for validating expiry dates
        Pattern expiryPattern = Pattern.compile(expiryFormat);
        Matcher matcher = expiryPattern.matcher(expiry);
        return matcher.matches();
    }

    /**
     * Checks if the length of the credit card number is within the acceptable range.
     *
     * @param cardNumber The credit card number to be checked.
     * @return true if the length of the credit card number is valid, false otherwise.
     */
    public boolean checkCardLength(String cardNumber) {
        return cardNumber.length() == CARDLENGTHFLOOR || cardNumber.length() == CARDLENGTHCEILING;
    }

}