package com.example.fakeapichallenge.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.fakeapichallenge.Constants
import com.example.fakeapichallenge.databinding.FragmentMoviesBinding
import com.example.fakeapichallenge.ui.adapters.MoviesAdapter
import com.example.fakeapichallenge.utils.NetworkResult
import com.example.fakeapichallenge.utils.observeOnce
import com.example.fakeapichallenge.viewModel.MovieViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoviesFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private val adapter by lazy { MoviesAdapter() }

    private var binding: FragmentMoviesBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMoviesBinding.inflate(inflater, container, false)

        initRecyclerView()
        observeLocalData()

        return binding?.root
    }

    private fun observeLocalData() {
        lifecycleScope.launch {
            movieViewModel.localMovies.observeOnce(viewLifecycleOwner) { entity ->
                if (entity.isNotEmpty()) {
                    Log.i(FRAGMENT_TAG, "Fetch local database called")
                    adapter.updateData(entity)
                } else {
                    observeRemoteData()
                }
            }
        }
    }

    private fun observeRemoteData() {
        Log.d(FRAGMENT_TAG, "Fetch API data called")
        movieViewModel.fetchMovies()

        // Observe data changes and update UI accordingly
        movieViewModel.remoteMovies.observe(viewLifecycleOwner) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    response.data?.let { movies ->
                        adapter.updateData(movies)
                    }
                }
                is NetworkResult.Error -> {
                    //loadMoviesFromCache()
                    Toast.makeText(this.context, response.message, Toast.LENGTH_SHORT).show()
                }
                is NetworkResult.Progress -> {}
            }
        }
    }

    /*private fun loadMoviesFromCache() {
        lifecycleScope.launch {
            movieViewModel.localMovies.observe(viewLifecycleOwner) { data ->
                if (data.isNotEmpty()) {
                    //adapter.updateData(data)
                }
            }
        }
    }*/

    private fun initViewModels() {
        movieViewModel = ViewModelProvider(this)[MovieViewModel::class.java]
    }

    private fun initRecyclerView() {
        binding?.apply {
            moviesRecyclerView.adapter = this@MoviesFragment.adapter
            moviesRecyclerView.layoutManager = GridLayoutManager(this@MoviesFragment.context, Constants.GRID_SPAN_COUNT)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }

    companion object {
        const val FRAGMENT_TAG = "moviesFragment"
    }
}