package com.example.group12.logic.validator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationValidator {
    private final String emailRegex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";

    public boolean isEmailEmpty(String email){
        return email.isEmpty();
    }
    public boolean isExperienceEmpty(String experience){
        return experience.isEmpty();
    }
    public boolean isNameEmpty(String name){
        return name.isEmpty();
    }

    /**
     * Checks if the given email address is valid.
     * @param email The email address to be validated.
     * @return True if the email address is valid, otherwise false.
     */
    public boolean isValidEmail(String email) {
        Pattern emailPattern = Pattern.compile(emailRegex);
        Matcher matcher = emailPattern.matcher(email);
        return matcher.matches();
    }
}
