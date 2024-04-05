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
 * UI Automator test class for employee payment functionality.
 */
public class UIAutomatorEmployeePayment {
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
        Intent intent = new Intent(context, Dashboard_User.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    /**
     * Test to check if the "My PayPal" button is visible.
     *
     * @throws UiObjectNotFoundException If the UI object is not found.
     */
    @Test
    public void checkIfMyPayPalButtonVisible() {
        // this test checks if the pay pal button is visible
        UiObject yourJobsButton = device.findObject(new UiSelector().text("My PayPal"));
        assertTrue(yourJobsButton.exists());
    }

    /**
     * Test to move to the "My PayPal" page and check if the "Pay Pal" option is visible.
     *
     * @throws UiObjectNotFoundException If the UI object is not found.
     */
    @Test
    public void moveToMyPayPalPage() throws UiObjectNotFoundException {
        // Move to the "My PayPal" page and check if the "Pay Pal" option is visible
        UiObject myPayPalButton = device.findObject(new UiSelector().text("My PayPal"));
        myPayPalButton.click();
        UiObject payPalFeature = device.findObject(new UiSelector().text("Connect my Payment ID"));
        assertTrue(payPalFeature.exists());
    }
}
