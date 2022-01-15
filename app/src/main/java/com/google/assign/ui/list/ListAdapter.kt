package com.google.assign.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.assign.databinding.ItemLayoutBinding
import com.google.assign.model.Cats

class ListAdapter : PagingDataAdapter<Cats, ListAdapter.UserViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class UserViewHolder(
        private val binding: ItemLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Cats) {
            binding.apply {
                this.cats = result
                pos.text = (absoluteAdapterPosition + 1).toString()
            }
        }

    }

}


val DiffUtil = object : DiffUtil.ItemCallback<Cats>() {
    override fun areItemsTheSame(oldItem: Cats, newItem: Cats): Boolean =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Cats, newItem: Cats): Boolean =
        newItem == oldItem
}

