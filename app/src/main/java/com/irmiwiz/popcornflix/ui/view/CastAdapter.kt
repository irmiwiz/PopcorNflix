package com.irmiwiz.popcornflix.ui.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.irmiwiz.popcornflix.BuildConfig
import com.irmiwiz.popcornflix.R
import com.irmiwiz.popcornflix.databinding.CastItemBinding
import com.irmiwiz.popcornflix.domain.model.Cast

class CastAdapter : ListAdapter<Cast, CastAdapter.CastViewHolder>(CastDiffCallback()) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CastViewHolder {
        val binding = CastItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CastViewHolder, position: Int) {
        holder.bind(currentList[position])
    }

    override fun getItemCount(): Int {
        return currentList.size
    }

    inner class CastViewHolder(private val binding: CastItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(cast: Cast) {
            binding.apply {
                name.text = cast.name
                Glide.with(itemView)
                    .load(BuildConfig.CAST_BASE_URL + cast.profilePath)
                    .centerCrop()
                    .error(R.drawable.ic_person)
                    .into(imageView)
            }
        }
    }
}

private class CastDiffCallback : DiffUtil.ItemCallback<Cast>() {

    override fun areItemsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Cast, newItem: Cast): Boolean {
        return oldItem == newItem
    }
}