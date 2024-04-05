package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;
import androidx.test.uiautomator.UiObject;
import androidx.test.uiautomator.UiObjectNotFoundException;
import androidx.test.uiautomator.UiScrollable;
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.group12.ui.user.Dashboard_User;

import org.junit.Before;
import org.junit.runner.RunWith;

/**
 * UI Automator test class for job listing functionality.
 */
@RunWith(AndroidJUnit4.class)
public class UIAutomatorJobListTest {

    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    /**
     * Set up method to launch the Dashboard_User activity.
     */
    @Before
    public void setup(){
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = new Intent(context, Dashboard_User.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test to check if job listings exist.
     * @throws UiObjectNotFoundException if the UI object is not found
     */
    public void checkIfJobListingExists() throws UiObjectNotFoundException {
        UiScrollable recyclerView = new UiScrollable(new UiSelector().scrollable(false));
        assertTrue(recyclerView.exists());
        recyclerView.scrollIntoView(new UiSelector().text("PAINTER"));
        UiObject jobTitle = device.findObject(new UiSelector().text("PAINTER"));
        assertTrue(jobTitle.exists());
        UiObject selectJob = device.findObject(new UiSelector().text("View Details"));
        assertTrue(selectJob.exists());
    }
}
