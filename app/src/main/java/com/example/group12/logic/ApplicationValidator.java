package com.example.group12.logic;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ApplicationValidator {

    public boolean isEmailEmpty(String email){
        return email.isEmpty();
    }
    public boolean isExperienceEmpty(String experience){
        return experience.isEmpty();
    }
    public boolean isNameEmpty(String name){
        return name.isEmpty();
    }


    public boolean isValidEmail(String email) {
        String email_format = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        Pattern email_pattern = Pattern.compile(email_format);
        Matcher matcher = email_pattern.matcher(email);
        return matcher.matches();
    }
}
