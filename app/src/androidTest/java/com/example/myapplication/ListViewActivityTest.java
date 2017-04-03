package com.example.myapplication;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static org.hamcrest.Matchers.allOf;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class ListViewActivityTest {

    @Rule
    public ActivityTestRule<ListViewActivity> mActivityTestRule = new ActivityTestRule<>(ListViewActivity.class);

    @Test
    public void listViewActivityTest() {
        ViewInteraction twoLineListItem = onView(
                allOf(childAtPosition(
                        allOf(withId(R.id.list_view),
                                withParent(withId(R.id.activity_list_view))),
                        0),
                        isDisplayed()));
        twoLineListItem.perform(click());

        ViewInteraction constraintLayout = onView(
                allOf(childAtPosition(
                        allOf(withId(android.R.id.list),
                                withParent(withId(android.R.id.content))),
                        6),
                        isDisplayed()));
        constraintLayout.perform(click());

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
