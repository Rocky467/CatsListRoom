package com.google.assignment.ui.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.google.assignment.R
import com.google.assignment.base.BaseFragment
import com.google.assignment.databinding.ListFragmentBinding
import com.google.assignment.db.Cats
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment(), ListAdapter.AdapterInterface {

    private lateinit var binding: ListFragmentBinding
    private lateinit var listAdapter: ListAdapter
    private val listViewModel: ListViewModel by viewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ListFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        observers()
    }

    private fun setupRecyclerView() {
        listAdapter = ListAdapter(this)
        binding.recyclerView.adapter = listAdapter

        binding.swipeRefresh.setOnRefreshListener {
            Handler(Looper.getMainLooper()).postDelayed({
                listAdapter.refresh()
                binding.swipeRefresh.isRefreshing = false
            }, 1000)
        }

        listAdapter.addLoadStateListener { loadState ->
            binding.loader.isVisible = loadState.mediator?.refresh is LoadState.Loading
        }

        binding.recyclerView.adapter = listAdapter.withLoadStateFooter(
            footer = ListLoadStateAdapter(listAdapter)
        )
    }

    private fun observers() {
        with(listViewModel) {
            lifecycleScope.launch {
                cats.collectLatest {
                    listAdapter.submitData(it)
                }
            }
        }
    }

    override fun itemClick(result: Cats) {
        sharedViewModel.result = result
        navigateTo(R.id.detailFragment)
    }

}
