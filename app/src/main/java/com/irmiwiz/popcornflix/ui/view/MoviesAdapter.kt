package com.irmiwiz.popcornflix.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.databinding.PosterItemBinding
import com.irmiwiz.popcornflix.domain.model.Movie

class MoviesAdapter(
  private val getMoreData: (movie: Movie) -> Unit,
  private val onMovieClicked: (movie: Movie) -> Unit
) : RecyclerView.Adapter<MoviesAdapter.MovieHolder>() {

  private val list: MutableList<Movie> = mutableListOf()

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
    val binding = PosterItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    return MovieHolder(binding)
  }

  override fun onBindViewHolder(holder: MovieHolder, position: Int) {
    holder.bind(list[position])
    if (position == list.size - 1) getMoreData(list[position])
  }

  override fun getItemCount(): Int {
    return list.size
  }

  fun updateList(newList: List<Movie>) {
    val start = list.size-1
    list.addAll(newList)
    notifyItemRangeInserted(start, newList.size.minus(1))
  }

  fun submitList(data: List<Movie>) {
    list.clear()
    list.addAll(data)
    notifyDataSetChanged()
  }

  inner class MovieHolder(private val binding: PosterItemBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(movie: Movie) {
      binding.apply {
        Glide.with(itemView)
          .load(BuildConfig.POSTER_BASE_URL + movie.posterPath)
          .centerCrop()
          .error(R.drawable.ic_warning)
          .into(imageView)

        imageView.setOnClickListener {
          onMovieClicked(movie)
        }
      }
    }
  }
}