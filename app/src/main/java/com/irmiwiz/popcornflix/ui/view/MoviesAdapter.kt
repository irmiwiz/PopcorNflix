package com.irmiwiz.popcornflix.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.databinding.PosterItemBinding
import com.irmiwiz.popcornflix.domain.model.Movie

class MoviesAdapter(
  private val listener: OnItemClickListener
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

  private var movies: MutableList<Movie> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
    val binding =
      PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieHolder(binding)
  }

  override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    holder.bind(movies[position], position)
  }

  override fun getItemCount(): Int {
    return movies.size
  }

  inner class MovieHolder(private val binding: PosterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie, pos: Int) {
      binding.apply {
        Glide.with(itemView)
          .load(BuildConfig.POSTER_BASE_URL + movie.posterPath)
          .centerCrop()
          .error(R.drawable.ic_warning)
          .into(imageView)

        imageView.setOnClickListener {
          listener.onItemClick(movie)
        }
      }

      if (pos == movies.size - 1)
        listener.getMoreMovies()

    }
  }

  fun onMoviesUpdated(newMovies: List<Movie>){
    val start = movies.size-1
    movies.addAll(newMovies)
    notifyItemRangeInserted(start, newMovies.size-1)
  }

  fun clear() {
    movies.clear()
    notifyDataSetChanged()
  }

  interface OnItemClickListener {
    fun onItemClick(movie: Movie)

    fun getMoreMovies()
  }
}