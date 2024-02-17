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
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@RunWith(AndroidJUnit4.class)
public class UIAutomatorTest_Employer_Dashboard {
    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    @Before
    public void setup(){
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = context.getPackageManager().getLaunchIntentForPackage(launcherPackage);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkIfEmployerDashboardPageIsVisible() {
        //this test method exists to check if the employer dashboard page is visible
        UiObject PostJobButton = device.findObject(new UiSelector().text("Post a job"));
        assertTrue(PostJobButton.exists());
        UiObject YourJobButton = device.findObject(new UiSelector().text("Your Jobs"));
        assertTrue(YourJobButton.exists());
        UiObject HelpAndSupportButton = device.findObject(new UiSelector().text("Help and Support"));
        assertTrue(HelpAndSupportButton.exists());
        UiObject YourProfileButton = device.findObject(new UiSelector().text("Your Profile"));
        assertTrue(YourProfileButton.exists());
    }

    @Test
    public void checkIfMoved2FindJobPage() throws UiObjectNotFoundException {
        //this test method exists to check if the Find Job page is visible
    }
    @Test
    public void checkIfMoved2YourJobsPage() throws UiObjectNotFoundException {
        //this test method exists to check if the Your Job page is visible
    }
    @Test
    public void checkIfMoved2HelpAndSupportPage() throws UiObjectNotFoundException {
        //this test method exists to check if the Help and Support page is visible
    }
    @Test
    public void checkIfMoved2YourProfile() throws UiObjectNotFoundException {
        //this test method exists to check if the Your Profile page is visible
    }
}