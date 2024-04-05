package com.example.group12.core;

/**
 * Constants class contains constant values used throughout the application.
 */
public class Constants {

    // Email validation messages
    public static final String EMAIL_EMPTY = "Please enter your email";
    public static final String EMAIL_INVALID = "Please enter a valid email";

    // Password validation messages
    public static final String PASSWORD_EMPTY = "Please enter password";
    public static final String PASSWORD_MISMATCH = "Passwords don't match";
    public static final String PASSWORD_INVALID = "Password should contain a capital letter and a number";
    public static final String PASSWORD_INVALID_LENGTH = "Password should contain 7–16 characters";

    // Firebase database link
    public static final String FIREBASE_LINK = "https://quickcash-197c8-default-rtdb.firebaseio.com/";

    // Spinner options for salary range
    public static final String SPINNER_SALARY_SELECT = "--Select Salary Range--";
    public static final String SPINNER_SALARY_RANGE_ONE = "15+$ per hour";
    public static final String SPINNER_SALARY_RANGE_TWO = "20+$ per hour";
    public static final String SPINNER_SALARY_RANGE_THREE = "30+$ per hour";
    public static final String SPINNER_SALARY_RANGE_FOUR = "40+$ per hour";
    public static final String SPINNER_SALARY_RANGE_FIVE = "50+$ per hour";
    public static final String SPINNER_SALARY_RANGE_SIX = "100+$ per hour";

    // Spinner options for duration range
    public static final String SPINNER_DURATION_SELECT = "--Select Duration Range--";
    public static final String SPINNER_DURATION_RANGE_ONE = "Less than 1 hour";
    public static final String SPINNER_DURATION_RANGE_TWO = "Under 5 hours";
    public static final String SPINNER_DURATION_RANGE_THREE = "Under 10 hours";
    public static final String SPINNER_DURATION_RANGE_FOUR = "Under 1 day";
    public static final String SPINNER_DURATION_RANGE_FIVE = "Under 5 days";
    public static final String SPINNER_DURATION_RANGE_SIX = "More than 5 days";

    // Spinner options for location range
    public static final String SPINNER_LOCATION_SELECT = "--Select a distance--";
    public static final String SPINNER_LOCATION_RANGE_ONE = "Within 500m";
    public static final String SPINNER_LOCATION_RANGE_TWO = "Within 1km";
    public static final String SPINNER_LOCATION_RANGE_THREE = "Within 2km";
    public static final String SPINNER_LOCATION_RANGE_FOUR = "Within 3km";
    public static final String SPINNER_LOCATION_RANGE_FIVE = "Within 5km";
    public static final String SPINNER_LOCATION_RANGE_SIX = "Within 10km";

}
