package com.example.group12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import com.example.group12.logic.UserCredentialValidator;

/**
 * JUnit test class for validating user credentials.
 */
public class JUnitUserCredentialValidator {

    UserCredentialValidator val;

    @Before
    public void setup(){
        val = new UserCredentialValidator();
    }

    @Test
    public void checkIfEmailEmpty(){
        assertTrue(val.isEmailEmpty(""));
        assertFalse(val.isEmailEmpty("email"));
    }

    @Test
    public void checkIfPasswordEmpty(){
        assertTrue(val.isPasswordEmpty(""));
        assertFalse(val.isPasswordEmpty("password"));
    }

    @Test
    public void checkIfEmailValid(){
        assertTrue(val.isValidEmailAddress("aa1111@dal.ca"));
        assertTrue(val.isValidEmailAddress("Chris_Boner@outlook.com"));
    }

    @Test
    public void checkIfEmailInvalid(){
        assertFalse(val.isValidEmailAddress("email"));
        assertFalse(val.isValidEmailAddress("email.com"));
        assertFalse(val.isValidEmailAddress("email@com"));
        assertFalse(val.isValidEmailAddress("email@.com"));
        assertFalse(val.isValidEmailAddress("email@dal_ca"));
        assertFalse(val.isValidEmailAddress("@dal.ca"));
    }

    @Test
    public void checkIfPasswordMatch(){
        assertTrue(val.isPasswordMatch("abc", "abc"));
    }

    @Test
    public void checkIfPasswordDiffer(){
        assertFalse(val.isPasswordMatch("123", "abc"));
    }

    @Test
    public void checkIfPasswordLength(){
        assertTrue(val.checkPasswordLength("1234567"));
        assertFalse(val.checkPasswordLength("123456"));
        assertFalse(val.checkPasswordLength("123456789ABCDEFGH"));
    }

    @Test
    public void checkIfPasswordValid(){
        assertTrue(val.isPasswordValid("Abcdefgh1"));
        assertTrue(val.isPasswordValid("AAAAAAAA1"));
        assertTrue(val.isPasswordValid("11111111A"));
    }

    @Test
    public void checkIfPasswordInvalid(){
        assertFalse(val.isPasswordValid("12345678"));
        assertFalse(val.isPasswordValid("AAAAAAAA"));
    }
}
