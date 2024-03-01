package com.example.group12.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserCredentialValidator {
    public boolean isEmailEmpty(String email){
        return email.isEmpty();
    }

    public boolean isPasswordEmpty(String password1){
        return password1.isEmpty();
    }

    /**
     * Check if the email address is valid, should contain a '@' and a valid tld
     * @param emailAddress
     * @return
     */
    public boolean isValidEmailAddress(String emailAddress) {
        String email_format = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern email_pattern = Pattern.compile(email_format);
        Matcher matcher = email_pattern.matcher(emailAddress);
        return matcher.matches();
    }


    public boolean isPasswordMatch(String p1, String p2){
        return p1.equals(p2);
    }

    public boolean checkPasswordLength(String password){
        return password.length() >= 7 && password.length() <= 16;
    }

    /**
     * check if a password is valid, should contain a capital letter and a number
     * @param password
     * @return
     */
    public boolean isPasswordValid(String password){
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);

        return matcher.matches();
    }
}
