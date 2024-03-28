package com.example.group12;

import com.example.group12.core.Constants;
import com.example.group12.logic.FilterJob;
import com.example.group12.logic.UserCredentialValidator;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;
import com.example.group12.logic.FilterJob;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit test class for validating the job filtering logic.
 */
public class JUnitJobFilter {

    FilterJob filter;

    @Before
    public void setup(){
        filter = new FilterJob();
    }

    @Test
    public void checkIfTitleMatches(){
        assertTrue(filter.containsParameters("PAINTER", "PAINTER"));
        assertTrue(filter.containsParameters("painter", "PAINTER"));
        assertTrue(filter.containsParameters("paint", "PAINTER"));
        assertFalse(filter.containsParameters("SHOVEL", "PAINTER"));
    }

    @Test
    public void checkIfSalaryMatches(){
        assertTrue(filter.containsSalary(Constants.SPINNER_SALARY_RANGE_ONE,  15));
        assertFalse(filter.containsSalary(Constants.SPINNER_SALARY_RANGE_THREE,  15));
    }

    @Test
    public void checkIfDurationMatches(){
        assertTrue(filter.containsDuration(Constants.SPINNER_DURATION_RANGE_TWO, 4));
        assertFalse(filter.containsDuration(Constants.SPINNER_DURATION_RANGE_TWO, 10));

    }
}
