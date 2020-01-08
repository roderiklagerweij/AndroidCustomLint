package com.rl.testlintapplication

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import org.junit.Test


class SomeActivityTest {
    @Test
    fun test() {
        onView(withId(R.id.test)).check(matches(isDisplayed()))
    }
}