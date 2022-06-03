package com.nhlynn.paging_hilt.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.nhlynn.paging_hilt.databinding.MovieItemBinding
import com.nhlynn.paging_hilt.delegate.MovieDelegate
import com.nhlynn.paging_hilt.model.MovieVO
import javax.inject.Inject

class MoviePagingAdapter(private val delegate: MovieDelegate) :
    PagingDataAdapter<MovieVO, MoviePagingAdapter.MyViewHolder>(DIFF_UTIL) {

    companion object {
        val DIFF_UTIL = object : DiffUtil.ItemCallback<MovieVO>() {
            override fun areItemsTheSame(oldItem: MovieVO, newItem: MovieVO): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: MovieVO, newItem: MovieVO): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }

    class MyViewHolder(val viewDataBinding: MovieItemBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root)


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val viewHolder = holder.viewDataBinding
        val item = getItem(position) ?: return

        viewHolder.apply {
            Glide.with(root).load(item.image)
                .into(ivPoster)

            tvName.text = item.name
            tvSpecs.text = item.species

            root.setOnClickListener {
                delegate.onViewMovie(item)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }
}