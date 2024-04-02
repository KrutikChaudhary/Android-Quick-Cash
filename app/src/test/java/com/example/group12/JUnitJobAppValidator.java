package com.example.group12;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import com.example.group12.logic.ApplicationValidator;

import org.junit.Before;
import org.junit.Test;

public class JUnitJobAppValidator {


        ApplicationValidator value;

        @Before
        public void setup(){
            value = new ApplicationValidator();
        }

        @Test
        public void checkIfNameEmpty(){
            assertTrue(value.isNameEmpty(""));
            assertFalse(value.isNameEmpty("Jane Doe"));
        }

    @Test
    public void checkExperienceEmpty(){
        assertTrue(value.isExperienceEmpty(""));
        assertFalse(value.isExperienceEmpty("Random text explaining applicants experience relevant to the job."));
    }
    @Test
    public void checkEmailEmpty(){
        assertTrue(value.isEmailEmpty(""));
        assertFalse(value.isEmailEmpty("test@test.ca"));
    }
    @Test
    public void checkIfEmailValid(){
        assertTrue(value.isValidEmail("test@test.ca"));
        assertTrue(value.isValidEmail("test123@test.ca"));
        assertTrue(value.isValidEmail("test@test.com"));
        assertTrue(value.isValidEmail("test123@test.com"));
    }
}
