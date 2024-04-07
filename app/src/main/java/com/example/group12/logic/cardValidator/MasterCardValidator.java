package com.example.group12.logic.cardValidator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MasterCardValidator extends CreditCardValidator{
    @Override
    public boolean isValidCardNum(String cardNumber) {
        String mastercardCardNum_format = "^5[1-5][0-9]{14}$";
        Pattern cardNum_pattern = Pattern.compile(mastercardCardNum_format);
        Matcher matcher = cardNum_pattern.matcher(cardNumber);
        return matcher.matches(); // Specific implementation for validating Mastercard card numbers
    }
}