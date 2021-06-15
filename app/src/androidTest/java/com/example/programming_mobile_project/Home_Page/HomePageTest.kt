package com.example.programming_mobile_project.Home_Page

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.ActivityTestRule
import com.android21buttons.fragmenttestrule.FragmentTestRule
import com.example.programming_mobile_project.MainActivity
import com.example.programming_mobile_project.R
import com.example.programming_mobile_project.chalet.ChaletFragment
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomePageTest {

    @Rule
    @JvmField
    val rule = ActivityTestRule(MainActivity::class.java)

    //test che controlla se un certo elemento(in questo caso quello della posizione 2) della recyclerView è cliccabile
    @Test
    fun recyclerTest(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(2, click()))
    }

    //test per vedere se la recyclerView viene mostrata in maniera corretta
    @Test
    fun recyclerView_onAppLaunch(){
        onView(withId(R.id.recyclerview)).check(matches(isDisplayed()))
    }

    //test che vede il corretto funzionamento dello scroll della recyclerView.
    @Test
    fun recyclerView_test(){
        val recyclerView = rule.activity.findViewById<RecyclerView>(R.id.recyclerview)
        val itemcount = recyclerView.adapter?.itemCount
        if (itemcount != null) {
            onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.scrollToPosition<RecyclerView.ViewHolder>(itemcount - 1)) //la recycler view viene scrollata fino alla posizione specifica
        }
    }

    //test per vedere se il titolo del primo item (nella posizione 0) della recyclerView è "pluto"
    val ITEMBELOWFIND = 0
    @Test
    fun recyclerView_Testing(){
        onView(withId(R.id.recyclerview)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(ITEMBELOWFIND, click()))
        val itemval = "pluto"
        onView(withText(itemval)).check(matches(isDisplayed()))
    }


}