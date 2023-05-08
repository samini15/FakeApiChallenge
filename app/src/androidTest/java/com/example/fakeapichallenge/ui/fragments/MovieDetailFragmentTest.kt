package com.example.fakeapichallenge.ui.fragments

import android.os.Bundle
import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.example.fakeapichallenge.MovieDetailFragment
import com.example.fakeapichallenge.R
import com.example.fakeapichallenge.model.MovieItem
import org.junit.Test

class MovieDetailFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<MovieDetailFragment>

    @Test
    fun fragmentDataWellDisplayedBasedOnArgumentTest() {
        val bundle = Bundle()
        val item = MovieItem(id = 1,"Stanley Kuberic", "https://www.themoviedb.org/t/p/w600_and_h900_bestv2/dyRZE1GxbMI03HY8CweDXWHbVZg.jpg", "The Shining", 1988)
        bundle.putParcelable("movie", item)

        fragmentScenario = launchFragmentInContainer(bundle, themeResId = R.style.Theme_FakeApiChallenge, Lifecycle.State.STARTED)

        onView(withId(R.id.movie_detail_title_textView)).check(matches(withText(item.title)))
        onView(withId(R.id.movie_detail_year_textView)).check(matches(withText(item.year.toString())))
        onView(withId(R.id.movie_detail_director_textView)).check(matches(withText(item.director)))
    }


}