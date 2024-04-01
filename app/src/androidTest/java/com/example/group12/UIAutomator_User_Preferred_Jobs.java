package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.group12.ui.user.Dashboard_User_PreferredJobs;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * UI test class for employer actions in the app.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomator_User_Preferred_Jobs {
    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, Dashboard_User_PreferredJobs.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfPreferencePageIsVisible() {
        UiObject preferredJobs = device.findObject(new UiSelector().text("My Preferred Jobs"));
        assertTrue(preferredJobs.exists());
        UiObject preferredLocation = device.findObject(new UiSelector().text("My Preferred Location"));
        assertTrue(preferredLocation.exists());
        UiObject preferredSalary = device.findObject(new UiSelector().text("My Preferred Salary"));
        assertTrue(preferredSalary.exists());
        UiObject preferredJobTitle = device.findObject(new UiSelector().text("My Preferred Job Title"));
        assertTrue(preferredJobTitle.exists());
        UiObject submit = device.findObject(new UiSelector().text("Submit"));
        assertTrue(submit.exists());
    }

    @Test
    public void testPreferredJobsHeaderVisibility() {
        UiObject header = device.findObject(new UiSelector().text("My Preferred Jobs"));
        assertTrue(header.exists());
    }

    /**
     * Test entering text into the "My Preferred Location" EditText.
     */
    @Test
    public void testEnterPreferredLocation() throws UiObjectNotFoundException {
        UiObject preferredLocation = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredLocation"));
        preferredLocation.setText("1333 South Park St, Halifax, NS B3J 2K9");
        assertEquals("1333 South Park St, Halifax, NS B3J 2K9", preferredLocation.getText());
    }

    /**
     * Test entering a preferred salary.
     */
    @Test
    public void testEnterPreferredSalary() throws UiObjectNotFoundException {
        UiObject preferredSalary = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredSalary"));
        preferredSalary.setText("14");
        assertEquals("14", preferredSalary.getText());
    }

    /**
     * Test entering a preferred job title.
     */
    @Test
    public void testEnterPreferredJobTitle() throws UiObjectNotFoundException {
        UiObject preferredJobTitle = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredJobTitle"));
        preferredJobTitle.setText("Cleaner");
        assertEquals("Cleaner", preferredJobTitle.getText());
    }

    /**
     * Test clicking the "Submit" button.
     */
    @Test
    public void testClickSubmitButton() throws UiObjectNotFoundException {
        UiObject submitButton = device.findObject(new UiSelector().resourceId("com.example.group12:id/buttonSubmit"));
        assertTrue(submitButton.exists());
        submitButton.click();
    }

    /**
     * Test if a toast message pops up when the user clicks the Submit button after entering preferences.
     */
    @Test
    public void testSubmitButtonToastMessage() throws UiObjectNotFoundException {
        // Enter preferences
        UiObject preferredLocation = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredLocation"));
        preferredLocation.setText("1333 South Park St, Halifax, NS B3J 2K9");

        UiObject preferredSalary = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredSalary"));
        preferredSalary.setText("14");

        UiObject preferredJobTitle = device.findObject(new UiSelector().resourceId("com.example.group12:id/editTextPreferredJobTitle"));
        preferredJobTitle.setText("Cleaner");

        // Click the Submit button
        UiObject submitButton = device.findObject(new UiSelector().resourceId("com.example.group12:id/buttonSubmit"));
        assertTrue(submitButton.exists());
        submitButton.click();

        // Wait for the toast message to appear
        UiObject toastMessage = device.findObject(new UiSelector().textContains("Preferences saved successfully"));
        assertTrue("Toast message not found", toastMessage.waitForExists(15000)); // Increased wait time to 15 seconds

        // Check if the toast message is visible
        assertTrue("Toast message not visible", toastMessage.exists() && toastMessage.isClickable());
    }



}