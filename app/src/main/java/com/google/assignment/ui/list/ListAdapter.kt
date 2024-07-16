package com.google.assignment.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.assignment.databinding.ItemLayoutBinding
import com.google.assignment.db.Cats

class ListAdapter(private val adapterInterface: AdapterInterface) :
    PagingDataAdapter<Cats, ListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, adapterInterface)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class UserViewHolder(
        private val binding: ItemLayoutBinding,
        private val adapterInterface: AdapterInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(result: Cats) {
            binding.apply {
                this.cats = result
                pos.text = (absoluteAdapterPosition + 1).toString()

                itemView.setOnClickListener {
                    adapterInterface.itemClick(result)
                }
            }
        }
    }

    interface AdapterInterface {
        fun itemClick(result: Cats)
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Cats>() {
            override fun areItemsTheSame(oldItem: Cats, newItem: Cats): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: Cats, newItem: Cats): Boolean =
                newItem == oldItem
        }
    }

}


