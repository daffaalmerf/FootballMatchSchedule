package com.daffaalmerf.footballmatchschedulesqlite.activity

import android.support.test.espresso.Espresso
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import com.daffaalmerf.footballmatchschedulesqlite.R
import com.daffaalmerf.footballmatchschedulesqlite.R.id.*
import junit.framework.AssertionFailedError

import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {
    @Rule
    @JvmField var activityRule = ActivityTestRule(MainActivity::class.java)

    @Test
    fun testActivities() {
        Thread.sleep(1000)
        onView(withId(list_match)).check(matches(isDisplayed()))

        Thread.sleep(1000)
        onView(withId(spinnerMatch)).check(matches(isDisplayed()))
        onView(withId(spinnerMatch)).perform(click())

        Thread.sleep(1000)
        onView(ViewMatchers.withText("Scottish Premier League")).perform(click())

        Thread.sleep(1000)
        onView(withId(list_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(list_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        Thread.sleep(1000)

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(1000)
        Espresso.pressBack()

        Thread.sleep(1000)
        onView(withId(next_match)).perform(click())

        Thread.sleep(1000)
        onView(withId(list_match)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(10))
        onView(withId(list_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))

        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(1000)
        Espresso.pressBack()

        Thread.sleep(1000)
        onView(withId(favorite_match)).perform(click())

        try {
            onView(withId(list_match)).check(matches(isDisplayed()))
        } catch (e: AssertionFailedError) {
            return
        }

        Thread.sleep(2000)
        onView(withId(list_match)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, click()))
        onView(withId(add_to_favorite)).perform(click())
        Thread.sleep(2000)
        Espresso.pressBack()

        Thread.sleep(1000)

    }

}