package com.main.prodapp.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.main.prodapp.R
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test

class SignInFragmentTest {

    private lateinit var scenario: FragmentScenario<SignInFragment>

    @Before
    fun setUp() {
        scenario = launchFragmentInContainer(themeResId = R.style.Theme_ProdApp)
    }

    @After
    fun tearDown() {
        scenario.close()
    }


    @Test
    fun emailHintTest() {
        onView(withId(R.id.emailField)).check(matches(withHint("Enter Email")))
    }

    @Test
    fun passwordHintTest() {
        onView(withId(R.id.passwordField)).check(matches(withHint("Enter Password")))
    }

    @Test
    fun appTitleTest(){
        onView(withId(R.id.sign_up_title)).check(matches(withText("ProdApp")))
    }


}