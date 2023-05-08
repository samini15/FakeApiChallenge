package com.example.fakeapichallenge.ui.fragments

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.fragment.app.testing.withFragment
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.example.fakeapichallenge.R
import com.example.fakeapichallenge.model.MovieItem
import com.example.fakeapichallenge.ui.adapters.MoviesAdapter

import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class MoviesFragmentTest {

    private lateinit var fragmentScenario: FragmentScenario<MoviesFragment>

    @Before
    fun setUp() {
        fragmentScenario = launchFragmentInContainer(themeResId = R.style.Theme_FakeApiChallenge)
        fragmentScenario.moveToState(Lifecycle.State.STARTED)
    }

    @Test
    fun fetchDataFirstFromLocalDatabaseOtherwiseFromApi() {

    }

    @Test
    fun clickingOnMovieRowNavigatesToDetailWithRightArguments() {
        val navController = mock(NavController::class.java)
        val item = MovieItem(id = 1,"Stanley Kuberic", "url", "The Shining", 1988)

        fragmentScenario.withFragment {
            Navigation.setViewNavController(requireView(), navController)
            adapter.movies = listOf(item)
        }

        onView(withId(R.id.movies_recyclerView)).perform(
            RecyclerViewActions.actionOnItemAtPosition<MoviesAdapter.MovieViewHolder>(0, click())
        )

        //val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(item)
        //verify(navController).navigate(action)
    }
}