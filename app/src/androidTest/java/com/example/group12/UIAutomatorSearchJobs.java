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
import androidx.test.uiautomator.UiSelector;
import androidx.test.uiautomator.Until;

import com.example.group12.ui.SearchJobActivity;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class UIAutomatorSearchJobs {

    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";
    @Before
    public void setup(){

        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        final Intent appIntent = new Intent(context, SearchJobActivity.class);
        appIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(appIntent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);


    }

    @Test
    public void searchJobExists(){
        UiObject searchParameter = device.findObject(new UiSelector().text("Search Job Title"));
        assertTrue(searchParameter.exists());
        UiObject salaryRange = device.findObject(new UiSelector().text("Select Salary Range"));
        assertTrue(salaryRange.exists());
        UiObject searchButton = device.findObject(new UiSelector().text("Search"));
        assertTrue(searchButton.exists());
    }
}
