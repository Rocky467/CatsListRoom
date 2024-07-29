package com.google.assignment.ui.list

import androidx.core.view.isVisible
import androidx.paging.LoadState
import com.google.assignment.R
import com.google.assignment.base.BaseFragment
import com.google.assignment.databinding.ListFragmentBinding
import com.google.assignment.db.Cats
import org.koin.androidx.viewmodel.ext.android.viewModel

class ListFragment : BaseFragment<ListFragmentBinding>(ListFragmentBinding::inflate),
    ListAdapter.AdapterInterface {

    private lateinit var listAdapter: ListAdapter
    private val listViewModel: ListViewModel by viewModel()

    override fun onViewCreated() {
        setupRecyclerView()
        observers()
    }

    private fun setupRecyclerView() {
        listAdapter = ListAdapter(this)
        binding.recyclerView.adapter = listAdapter

        binding.swipeRefresh.setOnRefreshListener {
            listAdapter.refresh()
            binding.swipeRefresh.isRefreshing = false
        }

        listAdapter.addLoadStateListener { loadState ->
            binding.loader.isVisible = loadState.mediator?.refresh is LoadState.Loading
        }

        binding.recyclerView.adapter = listAdapter.withLoadStateFooter(
            footer = ListLoadStateAdapter(listAdapter)
        )
    }

    private fun observers() {
        listViewModel.cats.observe(viewLifecycleOwner) {
            listAdapter.submitData(lifecycle, it)
        }
    }

    override fun itemClick(result: Cats) {
        sharedViewModel.result = result
        navigateTo(R.id.detailFragment)
    }

}
