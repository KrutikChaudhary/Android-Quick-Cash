package com.example.group12.logic.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * A utility class for validating user credentials such as email and password.
 */
public class UserCredentialValidator {

    /**
     * Checks if the provided email address is empty.
     * @param email The email address to be checked.
     * @return True if the email address is empty, false otherwise.
     */
    public boolean isEmailEmpty(String email){
        return email.isEmpty();
    }

    /**
     * Checks if the provided password is empty.
     * @param password1 The password to be checked.
     * @return True if the password is empty, false otherwise.
     */
    public boolean isPasswordEmpty(String password1){
        return password1.isEmpty();
    }

    /**
     * Checks if the provided email address is valid.
     * @param emailAddress The email address to be checked.
     * @return True if the email address is valid, false otherwise.
     */
    public boolean isValidEmailAddress(String emailAddress) {
        String email_format = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
        Pattern email_pattern = Pattern.compile(email_format);
        Matcher matcher = email_pattern.matcher(emailAddress);
        return matcher.matches();
    }

    /**
     * Checks if two passwords match.
     * @param p1 The first password.
     * @param p2 The second password.
     * @return True if the passwords match, false otherwise.
     */
    public boolean isPasswordMatch(String p1, String p2){
        return p1.equals(p2);
    }

    /**
     * Checks if the provided password meets the length criteria.
     * @param password The password to be checked.
     * @return True if the password meets the length criteria, false otherwise.
     */
    public boolean checkPasswordLength(String password){
        return password.length() >= 7 && password.length() <= 16;
    }

    /**
     * Checks if the provided password is valid.
     * A valid password should contain at least one capital letter and one digit.
     * @param password The password to be checked.
     * @return True if the password is valid, false otherwise.
     */
    public boolean isPasswordValid(String password){
        String passwordRegex = "^(?=.*[A-Z])(?=.*\\d).+$";
        Pattern passwordPattern = Pattern.compile(passwordRegex);
        Matcher matcher = passwordPattern.matcher(password);

        return matcher.matches();
    }
}
