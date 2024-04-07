package com.example.group12.logic.validator.card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VisaCardValidator extends CreditCardValidator{
    @Override
    public boolean isValidCardNum(String cardNumber) {
        String visaCardNumFormat = "^4[0-9]{12}(?:[0-9]{3})?$";
        Pattern cardNumPattern = Pattern.compile(visaCardNumFormat);
        Matcher matcher = cardNumPattern.matcher(cardNumber);
        return matcher.matches(); // Specific implementation for validating Visa card numbers
    }
}