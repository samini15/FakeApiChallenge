package com.example.fakeapichallenge

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.bumptech.glide.Glide
import com.example.fakeapichallenge.databinding.FragmentMovieDetailBinding

class MovieDetailFragment : Fragment() {

    private val args by navArgs<MovieDetailFragmentArgs>()

    private var binding: FragmentMovieDetailBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMovieDetailBinding.inflate(inflater, container, false)

        bindViewData()

        return binding?.root
    }

    private fun bindViewData() {
        val movie = args.movie
        binding?.apply {
            Glide.with(movieDetailImageView.context)
                .load(movie.posterUrl)
                .placeholder(R.drawable.ic_image_placeholder)
                .fitCenter()
                .into(movieDetailImageView)

            movieDetailTitleTextView.text = movie.title
            movieDetailYearTextView.text = movie.year.toString()
            movieDetailDirectorTextView.text = movie.director
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}