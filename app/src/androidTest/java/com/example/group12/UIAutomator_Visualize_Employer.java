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

import com.example.group12.ui.employer.Dashboard_Employer;
import com.example.group12.ui.employer.Dashboard_Employer_View_Stats;

import org.junit.Before;
import org.junit.Test;

public class UIAutomator_Visualize_Employer {
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
        Intent intent = new Intent(context, Dashboard_Employer_View_Stats.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
        device.wait(Until.hasObject(By.pkg(launcherPackage).depth(0)), LAUNCH_TIMEOUT);
    }

    @Test
    public void testHeaderVisibility() throws UiObjectNotFoundException {
        // Check if the "Employer Statistics" header is visible
        UiObject header = device.findObject(new UiSelector().text("Employer Statistics"));
        assertTrue("Employer Statistics header not found", header.exists());
    }

    @Test
    public void testPieChartVisibility() throws UiObjectNotFoundException {
        // Check if the pie chart is visible
        UiObject pieChart = device.findObject(new UiSelector().resourceId("com.example.group12:id/chart"));
        assertTrue("Pie chart not found", pieChart.exists());
    }

    @Test
    public void testJobsGraphTextVisibility() throws UiObjectNotFoundException {
        UiObject employerPostedJobs = device.findObject(new UiSelector().text("Employer Posted Jobs"));
        assertTrue("Employer Posted Jobs text not found", employerPostedJobs.exists());
        UiObject applicationsReceived = device.findObject(new UiSelector().text("Applications received"));
        assertTrue("Applications received text not found", applicationsReceived.exists());
    }

}