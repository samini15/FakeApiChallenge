package com.example.fakeapichallenge.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.fakeapichallenge.R
import com.example.fakeapichallenge.databinding.MoviesRowLayoutBinding
import com.example.fakeapichallenge.model.MovieItem
import com.example.fakeapichallenge.ui.fragments.MoviesFragmentDirections
import com.example.fakeapichallenge.utils.AdapterListDiffHelper

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var movies: List<MovieItem> = ArrayList()

    fun updateData(data: List<MovieItem>) {
        val diffCallBack = AdapterListDiffHelper(movies, data)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        movies = data
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MoviesRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val currentMovie = movies[position]
        holder.bind(currentMovie)
    }

    override fun getItemCount(): Int = movies.size

    class MovieViewHolder(private val binding: MoviesRowLayoutBinding) : RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        private lateinit var movieItem: MovieItem

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(movie: MovieItem) {
            this.movieItem = movie
            binding.apply {
                Glide.with(movieRowImageView.context)
                    .load(movie.posterUrl)
                    .placeholder(R.drawable.ic_image_placeholder)
                    .fitCenter()
                    .into(movieRowImageView)

                movieRowTitleTextView.text = movie.title
            }
        }

        override fun onClick(v: View?) {
            val action = MoviesFragmentDirections.actionMoviesFragmentToMovieDetailFragment(movieItem)
            binding.root.findNavController().navigate(action)
        }
    }
}