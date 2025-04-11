package com.main.prodapp.fragments.calendar

import android.icu.text.SimpleDateFormat
import androidx.compose.ui.text.intl.Locale
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.type.Date
import com.main.prodapp.R
import org.hamcrest.Matchers.allOf
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@RunWith(AndroidJUnit4::class)
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
        onView(withId(R.id.monthYearTV)).check(matches(isDisplayed()))
        onView(withId(R.id.calendarRecyclerView)).check(matches(isDisplayed()))
        onView(withId(R.id.dateSelectionHeader)).check(matches(withText("Please Select a Date")))
    }

    @Test
    fun prevClickTest(){
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, -1)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        val date = dateFormat.format(calendar.time)
        onView(withId(R.id.prevMonthButton)).perform(click())

        onView(withId(R.id.monthYearTV)).check(matches(withText(date.toString())))
    }

    @Test
    fun nextClickTest(){
        val calendar = Calendar.getInstance()
        calendar.add(Calendar.MONTH, 1)
        val dateFormat = SimpleDateFormat("MMMM yyyy")
        val date = dateFormat.format(calendar.time)
        onView(withId(R.id.nextMonthButton)).perform(click())

        onView(withId(R.id.monthYearTV)).check(matches(withText(date.toString())))
    }

    @Test
    fun dateSelectionHeaderTest() {
        onView(allOf(withId(R.id.cellDayText), withText("1"))).perform(click())

        onView(withId(R.id.dateSelectionHeader)).check(matches(withText("Things to do on")))
    }

    @Test
    fun dateSelectionDisplayTest() {
        val firstDayOfMonth = LocalDate.now().withDayOfMonth(1)
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val date = firstDayOfMonth.format(formatter)

        onView(allOf(withId(R.id.cellDayText), withText("1"))).perform((click()))
        onView(withId(R.id.dateSelected)).check(matches(withText(date.toString())))
    }

}