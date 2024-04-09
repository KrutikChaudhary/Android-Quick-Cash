package com.example.group12;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import androidx.test.core.app.ActivityScenario;

import org.junit.Before;
import org.junit.Test;

import com.example.group12.ui.loginRegister.RegisterActivity;

/**
 * Espresso test class for registration functionality.
 */
public class EspressoRegistration {

    public ActivityScenario<RegisterActivity> scenario;

    /**
     * Set up method to launch the RegisterActivity and perform initial setup.
     */
    @Before
    public void setup(){
        scenario = ActivityScenario.launch(RegisterActivity.class);
        scenario.onActivity(RegisterActivity::signupButtonSetup);
    }

    /**
     * Test case for email field being empty.
     */
    @Test
    public void caseEmailEmpty(){
        onView(withId(R.id.enterEmailText)).perform(typeText(""));
        onView(withId(R.id.enterPasswordText)).perform(typeText("123"));
        onView(withId(R.id.confirmPasswordText)).perform(typeText("123"));
        onView(withId(R.id.emailLabel)).check(matches(withText("")));
        onView(withId(R.id.passwordLabel)).check(matches(withText("")));
    }
}
