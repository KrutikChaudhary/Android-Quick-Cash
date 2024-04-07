package com.example.group12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.group12.logic.validator.cardValidator.CreditCardValidator;
import com.example.group12.logic.validator.cardValidator.CvvValidator;
import com.example.group12.logic.validator.cardValidator.MasterCardValidator;
import com.example.group12.logic.validator.cardValidator.VisaCardValidator;

import org.junit.Before;
import org.junit.Test;

/**
 * JUnit test class for validating the CreditCardValidator class.
 */
public class JUnitCreditCardValidator {

    CreditCardValidator value;
    CvvValidator cvvValidator;

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
        cvvValidator = new CvvValidator();
        assertTrue(cvvValidator.isCVVEmpty(""));
        assertFalse(cvvValidator.isCVVEmpty("12"));
    }

    @Test
    public void checkIfExpiryEmpty(){
        assertTrue(value.isExpiryEmpty(""));
        assertFalse(value.isExpiryEmpty("expiry"));
    }

    @Test
    public void checkIfCVVInvalid(){
        cvvValidator = new CvvValidator();
        assertFalse(cvvValidator.isValidCVV("42A"));
        assertFalse(cvvValidator.isValidCVV("ABA"));
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
        cvvValidator = new CvvValidator();
        assertTrue(cvvValidator.checkCVVLength("123"));
        assertFalse(cvvValidator.checkCVVLength("12"));
        assertFalse(cvvValidator.checkCVVLength("1212"));
    }

    @Test
    public void checkIFVisaCardValid(){
        value = new VisaCardValidator();
        assertTrue(value.isValidCardNum("4916123456789012"));
    }

    @Test
    public void checkIFMasterCardValid(){
        value = new MasterCardValidator();
        assertTrue(value.isValidCardNum("5123456789012345"));
    }
}