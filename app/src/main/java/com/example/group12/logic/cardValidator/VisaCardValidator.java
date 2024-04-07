package com.example.group12.logic.cardValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisaCardValidator extends CreditCardValidator{
    @Override
    public boolean isValidCardNum(String cardNumber) {
        String visaCardNum_format = "^4[0-9]{12}(?:[0-9]{3})?$";
        Pattern cardNum_pattern = Pattern.compile(visaCardNum_format);
        Matcher matcher = cardNum_pattern.matcher(cardNumber);
        return matcher.matches(); // Specific implementation for validating Visa card numbers
    }
}