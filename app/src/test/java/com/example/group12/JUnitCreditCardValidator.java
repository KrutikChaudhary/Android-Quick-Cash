package com.example.group12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.group12.logic.CreditCardValidator;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for validating the CreditCardValidator class.
 */
public class JUnitCreditCardValidator {

    CreditCardValidator value;

    @Before
    public void setup(){
        value = new CreditCardValidator();
    }

    @Test
    public void checkIfCardNumEmpty(){
        assertTrue(value.isCardNumEmpty(""));
        assertFalse(value.isCardNumEmpty("num"));
    }

    @Test
    public void checkIfCVVEmpty(){
        assertTrue(value.isCVVEmpty(""));
        assertFalse(value.isCVVEmpty("cvv"));
    }

    @Test
    public void checkIfExpiryEmpty(){
        assertTrue(value.isExpiryEmpty(""));
        assertFalse(value.isExpiryEmpty("expiry"));
    }

    @Test
    public void checkIfCVVInvalid(){
        assertFalse(value.isValidCVV("42A"));
        assertFalse(value.isValidCVV("ABA"));
    }

    @Test
    public void checkIfCardNumInvalid(){
        assertFalse(value.isValidCardNum("423456789098"));
        assertFalse(value.isValidCardNum("22345678909876541"));
    }

    @Test
    public void checkIfExpiryInvalid(){
        assertFalse(value.isValidExpiry("12 23"));
        assertFalse(value.isValidExpiry("1223"));
        assertFalse(value.isValidExpiry("123"));
        assertFalse(value.isValidExpiry("80/01"));
    }

    @Test
    public void checkIfCVVLength(){
        assertTrue(value.checkCVVLength("123"));
        assertFalse(value.checkCVVLength("12"));
        assertFalse(value.checkCVVLength("1212"));
    }
}
