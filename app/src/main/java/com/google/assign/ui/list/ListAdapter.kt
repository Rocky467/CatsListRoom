package com.google.assign.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.assign.databinding.ItemLayoutBinding
import com.google.assign.db.UserEntity
import com.google.assign.model.User

class ListAdapter(private val userInterface: UserInterface) :
    PagingDataAdapter<UserEntity, ListAdapter.UserViewHolder>(DiffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return UserViewHolder(binding, userInterface)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    class UserViewHolder(
        private val binding: ItemLayoutBinding,
        private val userInterface: UserInterface
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(user: UserEntity) {
            binding.apply {
                this.user = user
                pos.text = (absoluteAdapterPosition + 1).toString()
            }

            binding.item.setOnClickListener {
                userInterface.userClick(user)
            }
        }

    }

}


interface UserInterface {
    fun userClick(user: UserEntity)
}


val DiffUtil = object : DiffUtil.ItemCallback<UserEntity>() {
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
        oldItem.firstName == newItem.firstName

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean =
        newItem == oldItem
}

