package com.example.group12.logic.validator.card;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasterCardValidator extends CreditCardValidator{
    @Override
    public boolean isValidCardNum(String cardNumber) {
        String mastercardCardNumFormat = "^5[1-5][0-9]{14}$";
        Pattern cardNumPattern = Pattern.compile(mastercardCardNumFormat);
        Matcher matcher = cardNumPattern.matcher(cardNumber);
        return matcher.matches(); // Specific implementation for validating Mastercard card numbers
    }
}