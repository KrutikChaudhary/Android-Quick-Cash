package com.example.group12;

import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.group12.ui.employer.Dashboard_Employer;

/**
 * UI Automator test class for Employer Dashboard functionality.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest_Employer_Dashboard {
    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    /**
     * Set up method to launch the Employer Dashboard activity.
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, Dashboard_Employer.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test to check if the elements related to Employer Dashboard are visible.
     */
    @Test
    public void checkIfEmployerDashboardPageIsVisible() {
        UiObject yourJobsButton = device.findObject(new UiSelector().text("Your Jobs"));
        assertTrue(yourJobsButton.exists());
        UiObject postJobButton = device.findObject(new UiSelector().text("Post a Job"));
        assertTrue(postJobButton.exists());
        UiObject yourProfileButton = device.findObject(new UiSelector().text("View Stats"));
        assertTrue(yourProfileButton.exists());
    }

    /**
     * Test to check if moving to the Post Job page is successful.
     */
    @Test
    public void checkIfMoved2PostJobPage() throws UiObjectNotFoundException {
        Espresso.onView(ViewMatchers.withId(R.id.postJobButton)).perform(click());

        // Check if the post job page is visible by asserting that a view in the post job page is displayed
        Espresso.onView(ViewMatchers.withId(R.id.employerPostJob)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()));
    }

    /**
     * Test to check if moving to the Your Jobs page is successful.
     */
    @Test
    public void checkIfMoved2YourJobsPage() throws UiObjectNotFoundException {
        // Test method implementation
    }

    @Test
    public void checkIfStatsButtonVisible() {
        UiObject viewStatsButton = device.findObject(new UiSelector().text("View Stats"));
        assertTrue(viewStatsButton.exists());
    }

    @Test
    public void checkIfMoveToStatsPage() throws UiObjectNotFoundException {
        UiObject viewStatsButton = device.findObject(new UiSelector().text("View Stats"));
        assertTrue(viewStatsButton.exists());
        viewStatsButton.click();
    }
}
