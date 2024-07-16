package com.google.assign.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.assign.R
import com.google.assign.databinding.NetworkStateItemBinding

class ListLoadStateAdapter(private val adapter: ListAdapter) :
    LoadStateAdapter<ListLoadStateAdapter.NetworkStateViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): NetworkStateViewHolder {
        val binding = NetworkStateItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NetworkStateViewHolder(binding, adapter)
    }

    override fun onBindViewHolder(holder: NetworkStateViewHolder, loadState: LoadState) {
        holder.bind(loadState)
    }

    class NetworkStateViewHolder(
        private val binding: NetworkStateItemBinding,
        private val adapter: ListAdapter
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(loadState: LoadState) {
            binding.apply {
                progressBar.isVisible = loadState is LoadState.Loading
                binding.retryButton.isVisible = loadState is LoadState.Error
                errorMsg.isVisible = !(loadState as? LoadState.Error)?.error?.message.isNullOrBlank()
                errorMsg.text = binding.root.context.getString(R.string.no_internet_try)

                retryButton.setOnClickListener {
                    adapter.retry()
                }
            }
        }
    }
}
