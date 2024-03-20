package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.example.group12.ui.user.Dashboard_User;

/**
 * UI Automator test class to verify the functionality of the user dashboard.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest_User_Dashboard {

    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    /**
     * Set up method to launch the Dashboard_User activity and wait until it's launched.
     */
    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, Dashboard_User.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test to check if the user dashboard page is visible.
     */
    @Test
    public void checkIfUserDashboardPageIsVisible() {
        // this test checks if the User Dashboard page is visible
        UiObject FindJobButton = device.findObject(new UiSelector().text("Find Jobs"));
        assertTrue(FindJobButton.exists());
        UiObject AllJobButton = device.findObject(new UiSelector().text("All Jobs"));
        assertTrue(AllJobButton.exists());
        UiObject YourProfileButton = device.findObject(new UiSelector().text("Your Profile"));
        assertTrue(YourProfileButton.exists());
    }

    /**
     * Test to check if the user can navigate to the Find Jobs page.
     */
    @Test
    public void checkIfMoved2FindJobsPage() throws UiObjectNotFoundException {
        // checks if the user can navigate to find Jobs page
    }

    /**
     * Test to check if the user can navigate to the All Jobs page.
     */
    @Test
    public void checkIfMoved2AllJobsPage() throws UiObjectNotFoundException {
        // checks if the user can navigate to All Jobs page
    }

    /**
     * Test to check if the user can navigate to the Stats page.
     */
    @Test
    public void checkIfMoved2StatsPage() throws UiObjectNotFoundException {
        // this test checks if user can navigate to stats page
    }

    /**
     * Test to check if the user can navigate to the Profile page.
     */
    @Test
    public void checkIfMoved2ProfilePage() throws UiObjectNotFoundException {
        // this test checks if user can navigate to Profile page
    }
}