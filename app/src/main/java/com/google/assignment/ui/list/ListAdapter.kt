package com.google.assignment.ui.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.assignment.databinding.ItemLayoutBinding
import com.google.assignment.db.Cats
import com.google.assignment.utils.Util.diffUtil

class ListAdapter(private val adapterInterface: AdapterInterface) :
    PagingDataAdapter<Cats, ListAdapter.UserViewHolder>(diffUtil { old, new -> old.id == new.id }) {

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

        @SuppressLint("SetTextI18n")
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

}


