package com.example.group12.logic.cardValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CvvValidator {
    public static final int CVVLENGTH = 3; // Length of CVV number

    /**
     * Checks if the length of the CVV number is valid.
     * @return true if the length of the CVV number is valid, false otherwise.
     */
    public boolean checkCVVLength(String cvv){
        return cvv.length() == CVVLENGTH;
    }

    /**
     * Checks if the CVV number is empty.
     * @return true if the CVV number is empty, false otherwise.
     */
    public boolean isCVVEmpty(String cvv){
        return cvv.isEmpty();
    }

    /**
     * Checks if the CVV number is valid based on a regex pattern.
     * @return true if the CVV number is valid, false otherwise.
     */
    public boolean isValidCVV(String cvv) {
        String cvv_format = "\\\\d{3}"; // Regular expression pattern for validating CVV numbers
        Pattern cvv_pattern = Pattern.compile(cvv_format);
        Matcher matcher = cvv_pattern.matcher(cvv);
        return matcher.matches();
    }
}