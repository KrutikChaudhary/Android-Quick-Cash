package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.group12.ui.user.Dashboard_User;

import org.junit.Before;
import org.junit.Test;

/**
 * UI Automator test class to verify the visibility of the "View Details" button
 * and the navigation to the job details page.
 */
public class UIAutomatorViewDetails {

    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    /**
     * Set up method to launch the Dashboard_User activity and wait until it's launched.
     */
    @Before
    public void setup(){
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, Dashboard_User.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test to check if the "View Details" button is visible.
     */
    @Test
    public void checkIfViewDetailsVisible() {
        // this test checks if the view details button is visible
        UiObject viewDetails = device.findObject(new UiSelector().text("View Details"));
        assertTrue(viewDetails.exists());
    }

    /**
     * Test to check if the navigation to the job details page is successful.
     * @throws UiObjectNotFoundException if the UI object is not found.
     */
    @Test
    public void checkIfNavigateToJobDetailsPage() throws UiObjectNotFoundException {
        // this test checks if the navigation
        UiObject viewDetails = device.findObject(new UiSelector().text("View Details"));
        viewDetails.click();
        UiObject apply = device.findObject(new UiSelector().text("Apply"));
        assertTrue(apply.exists());
    }

    @Test
    public void checkIfViewOnMapsVisible() throws UiObjectNotFoundException {
        // this test checks if the navigation
        UiObject viewDetails = device.findObject(new UiSelector().text("View Details"));
        viewDetails.click();
        UiObject mapButton = device.findObject(new UiSelector().text("View on Maps"));
        assertTrue(mapButton.exists());
    }
}