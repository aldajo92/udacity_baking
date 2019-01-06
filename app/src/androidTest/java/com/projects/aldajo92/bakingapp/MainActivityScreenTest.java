package com.projects.aldajo92.bakingapp;

import com.projects.aldajo92.bakingapp.main.MainActivity;
import com.projects.aldajo92.bakingapp.util.FetchingIdlingResource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import androidx.test.core.app.ActivityScenario;
import androidx.test.espresso.IdlingRegistry;
import androidx.test.espresso.contrib.RecyclerViewActions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withId;

@RunWith(AndroidJUnit4.class)
public class MainActivityScreenTest {

    private FetchingIdlingResource idlingResource;

    @Before
    public void registerIdlingResource() {
        ActivityScenario activityScenario = ActivityScenario.launch(MainActivity.class);
        activityScenario.onActivity((ActivityScenario.ActivityAction<MainActivity>) activity -> {
            idlingResource = activity.getIdlingResource();
            // To prove that the test fails, omit this call:
            IdlingRegistry.getInstance().register(idlingResource);
        });
    }

    @Test
    public void checkScrollRecyclerView_MainActivity() {
        onView(ViewMatchers.withId(R.id.recyclerView)).check(matches(isDisplayed()));
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.scrollToPosition(1));
    }

    @Test
    public void clickRecipeRecyclerViewItem_DetailActivity() {
        onView(ViewMatchers.withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        onView(ViewMatchers.withId(R.id.recyclerview_ingredient)).check(matches(isDisplayed()));

        onView(ViewMatchers.withId(R.id.recyclerview_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));

        onView(ViewMatchers.withId(R.id.tabLayout_step)).check(matches(isDisplayed()));
    }

    @Test
    public void clickStepRecyclerViewItem_SwipeRecipeSteps() {
        onView(withId(R.id.recyclerView)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.recyclerview_step)).perform(RecyclerViewActions.actionOnItemAtPosition(0, click()));
        onView(withId(R.id.view_pager_content_view)).perform(swipeLeft());
    }

    @After
    public void unregisterIdlingResource() {
        if (idlingResource != null) {
            IdlingRegistry.getInstance().unregister(idlingResource);
        }
    }
}
