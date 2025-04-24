package com.main.prodapp.fragments

import android.widget.DatePicker
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.PickerActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withClassName
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.main.prodapp.R
import com.main.prodapp.fragments.todo.TodoListFragment
import org.hamcrest.Matchers.equalTo

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoListFragmentTest {

    private lateinit var scenario: FragmentScenario<TodoListFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer<TodoListFragment>(themeResId = R.style.Theme_ProdApp)
    }

    @After
    fun tearDown() {
    }

    @Test
    fun initialDisplayTest() {
        onView(withId(R.id.editTextTitle)).check(matches(withHint("Title")))
        onView(withId(R.id.editTextDes)).check(matches(withHint("Description")))
    }

    @Test
    fun datePickerTest() {
        onView(withId(R.id.editDateButton)).perform(click())
        onView(withClassName(equalTo(DatePicker::class.java.name)))
            .perform(PickerActions.setDate(2025, 4, 15))
        onView(withText("OK")).perform(click())

        onView(withId(R.id.editDateButton)).check(matches(withText("2025-04-15")))
    }

}