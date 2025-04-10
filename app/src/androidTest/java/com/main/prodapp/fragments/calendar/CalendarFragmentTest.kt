package com.main.prodapp.fragments.calendar

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.main.prodapp.R
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class CalendarFragmentTest {

    private lateinit var scenario: FragmentScenario<CalendarFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<CalendarFragment>(themeResId = R.style.Theme_ProdApp)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun initialDisplayTest() {
        scenario.onFragment { fragment ->
            assertNotNull(fragment.view)
        }

        onView(withId(R.id.monthYearTV)).check(matches(isDisplayed()))
        onView(withId(R.id.calendarRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.dateSelectionHeader)).check(matches(withText("Please Select a Date")))
    }

}