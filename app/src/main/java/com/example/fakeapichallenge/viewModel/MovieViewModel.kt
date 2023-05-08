package com.example.fakeapichallenge.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.fakeapichallenge.data.local.LocalRepository
import com.example.fakeapichallenge.data.remote.RemoteRepository
import com.example.fakeapichallenge.model.MovieItem
import com.example.fakeapichallenge.model.Movies
import com.example.fakeapichallenge.utils.ConnectivityUtils
import com.example.fakeapichallenge.utils.NetworkResult
import com.example.fakeapichallenge.utils.NetworkUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieViewModel @Inject constructor(
    application: Application,
    private val localRepository: LocalRepository<MovieItem>,
    private val remoteRepository: RemoteRepository<Movies>
) : AndroidViewModel(application) {

    val localMovies : LiveData<List<MovieItem>> = localRepository.readEntities().asLiveData()

    var remoteMovies: MutableLiveData<NetworkResult<Movies>> = MutableLiveData()

    fun fetchMovies() = viewModelScope.launch {
        getMoviesSafeCall()
    }

    private fun insertMovie(movieEntity: MovieItem) = viewModelScope.launch(Dispatchers.IO) {
        localRepository.insertEntity(movieEntity)
    }

    private suspend fun getMoviesSafeCall() {
        remoteMovies.value = NetworkResult.Progress()

        if (ConnectivityUtils.hasInternetConnection(getApplication())) {
            val response = remoteRepository.fetchRemoteData()
            remoteMovies.value = NetworkUtils.handleApiResponse(response)

            // Caching data fetched to local DB
            remoteMovies.value?.data?.let { currentMovies ->
                offlineCacheMovies(currentMovies)
            }
        } else {
            remoteMovies.value = NetworkResult.Error("No Internet Connection")
        }

    }

    private fun offlineCacheMovies(currentMovies: Movies) {
        currentMovies.forEach {movie ->
            insertMovie(movie)
        }
    }
}