package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

public class Espresso_Employer_PostJobDetails {
    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    @Before
    public void setup() {

        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, Dashboard_Employer_PostJob.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }
    @Test
    public void checkJobTitleEditTextVisibility() {
        // Check if the Date EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextJobTitle))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }
    @Test
    public void checkDateEditTextVisibility() {
        // Check if the Date EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextDate))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkExpectedDurationEditTextVisibility() {
        // Check if the Expected Duration EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextExpectedDuration))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkUrgencyEditTextVisibility() {
        // Check if the Urgency EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextUrgency))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkSalaryEditTextVisibility() {
        // Check if the Salary EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextSalary))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    @Test
    public void checkJobLocationEditTextVisibility() {
        // Check if the Job Location EditText is visible
        Espresso.onView(ViewMatchers.withId(R.id.editTextJobLocation))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

}
