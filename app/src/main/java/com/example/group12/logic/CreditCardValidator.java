package com.example.group12.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CreditCardValidator {

    public boolean isCVVEmpty(String cvv){
        return cvv.isEmpty();
    }

    public boolean isCardNumEmpty(String num){
        return num.isEmpty();
    }
    public boolean isExpiryEmpty(String expiry){
        return expiry.isEmpty();
    }
    public boolean isValidCardNum(String cardNumber) {
        String cardNum_format = "";
        Pattern cardNum_pattern = Pattern.compile(cardNum_format);
        Matcher matcher = cardNum_pattern.matcher(cardNumber);
        return matcher.matches();
    }

    public boolean isValidCVV(String cvv) {
        String cvv_format = "";
        Pattern cvv_pattern = Pattern.compile(cvv_format);
        Matcher matcher = cvv_pattern.matcher(cvv);
        return matcher.matches();
    }

    public boolean isValidExpiry(String expiry) {
        String expiry_format = "";
        Pattern expiry_pattern = Pattern.compile(expiry_format);
        Matcher matcher = expiry_pattern.matcher(expiry);
        return matcher.matches();
    }
    public boolean checkCardLength(String cardNumber){
        return cardNumber.length() == 13 || cardNumber.length() == 16;
    }
    public boolean checkCVVLength(String cvv){
        return cvv.length() == 3;
    }
}
