package com.example.group12;

import com.example.group12.core.Constants;
import com.example.group12.logic.FilterJob;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
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
        assertTrue(filter.containsSalary(Constants.SPINNER_SALARY_RANGE_ONE, (float) 15.5));
        assertFalse(filter.containsSalary(Constants.SPINNER_SALARY_RANGE_THREE, (float) 15.5));
    }

    @Test
    public void checkIfDurationMatches(){
        assertTrue(filter.containsDuration(Constants.SPINNER_DURATION_RANGE_TWO, 4));
        assertFalse(filter.containsDuration(Constants.SPINNER_DURATION_RANGE_TWO, 10));

    }
}
