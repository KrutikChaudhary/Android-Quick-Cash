package com.example.group12;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;

import static org.junit.Assert.assertTrue;

import android.content.Context;
import android.content.Intent;


import androidx.test.core.app.ApplicationProvider;
import androidx.test.uiautomator.By;
import androidx.test.uiautomator.UiDevice;

import androidx.test.uiautomator.Until;

import org.junit.Before;
import org.junit.Test;

import com.example.group12.ui.MainActivity;

public class UIAutomatorTest_LocationDetector {
    private UiDevice device;
    private static final int LAUNCH_TIMEOUT = 5000;
    final String launcherPackage = "com.example.group12";

    @Before
    public void setup() {
        device = UiDevice.getInstance(getInstrumentation());
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void checkLocationPermissionDialog(){
        // This test case assumes that Device has not granted the location permission to the app.
        boolean isPermissionDialogShown = device.wait(Until.hasObject(By.textContains("allow")), 5000);
        assertTrue(isPermissionDialogShown);
    }
}