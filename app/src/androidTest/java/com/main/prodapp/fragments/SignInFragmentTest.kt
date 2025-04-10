package com.main.prodapp.fragments

import android.content.Context
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withHint
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.main.prodapp.R
import org.junit.Assert.*

import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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

    @Test
    fun signUpButtonTest() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val navController = TestNavHostController(context)

        val scenario = launchFragmentInContainer<SignInFragment>(themeResId = R.style.Theme_ProdApp)

        scenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            navController.setCurrentDestination(R.id.signInFragment)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(withId(R.id.move_to_sign_up_button)).perform(click())

        assertEquals(R.id.signUpFragment, navController.currentDestination?.id)
    }



}