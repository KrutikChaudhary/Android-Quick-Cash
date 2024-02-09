package com.example.group12;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    protected boolean isEmailEmpty(String email){
        return email.isEmpty();
    }

    protected boolean isPasswordEmpty(String password1){
        return password1.isEmpty();
    }

    /**
     * Check if the email address is valid, should contain a '@' and a valid tld
     * @param emailAddress
     * @return
     */
    protected boolean isValidEmailAddress(String emailAddress) {
        String email_format = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern email_pattern = Pattern.compile(email_format);
        Matcher matcher = email_pattern.matcher(emailAddress);
        return matcher.matches();
    }


    protected boolean isPasswordMatch(String p1, String p2){
        return p1.equals(p2);
    }

    protected boolean checkPasswordLength(String password){
        return password.length() >= 7 && password.length() <= 16;
    }

    /**
     * check if a password is valid, should contain a capital letter and a number
     * @param password
     * @return
     */
    protected boolean isValidPassword(String password){
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);

        return matcher.matches();
    }
}
