package edu.tacoma.uw.finalproject;

import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.rule.ActivityTestRule;

import org.hamcrest.core.IsNot;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Random;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.action.ViewActions.typeText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.RootMatchers.withDecorView;
import static androidx.test.espresso.matcher.ViewMatchers.isChecked;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;

@RunWith(AndroidJUnit4.class)
public class SignInTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void testValidLogin(){
        //this user already exist in the login table
        String username = "user";
        String pass = "12345678";
        onView(withId(R.id.login_username)).perform(typeText(username));
        onView(withId(R.id.login_username)).perform(closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText(pass));
        onView(withId(R.id.login_password)).perform(closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());

        onView(withText("SIGN OUT"))
                .inRoot(withDecorView(not(is(
                        mActivityRule.getActivity()
                                .getWindow()
                                .getDecorView()))))
                .check(matches(isDisplayed()));

    }

    @Test
    public void testInValidLoginUsername(){
        Random random = new Random();
        String username = "user" + (random.nextInt(10000) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);
        String pass = "12345678";
        onView(withId(R.id.login_username)).perform(typeText(username));
        onView(withId(R.id.login_username)).perform(closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText(pass));
        onView(withId(R.id.login_password)).perform(closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Username or password was incorrect, please register before sign in if you haven't done so."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));

    }

    @Test
    public void testInValidLoginPassword(){
        Random random = new Random();
        String username = "user";
        String pass = "" + (random.nextInt(10000) + 1)
                + (random.nextInt(900) + 1) + (random.nextInt(700) + 1)
                + (random.nextInt(400) + 1) + (random.nextInt(100) + 1);
        onView(withId(R.id.login_username)).perform(typeText(username));
        onView(withId(R.id.login_username)).perform(closeSoftKeyboard());

        onView(withId(R.id.login_password)).perform(typeText(pass));
        onView(withId(R.id.login_password)).perform(closeSoftKeyboard());

        onView(withId(R.id.loginButton)).perform(click());
        onView(withText("Username or password was incorrect, please register before sign in if you haven't done so."))
                .inRoot(withDecorView(not(is(mActivityRule.getActivity().
                        getWindow().getDecorView())))).check(matches(isDisplayed()));

    }
}
