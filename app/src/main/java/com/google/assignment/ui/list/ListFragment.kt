package com.google.assignment.ui.list

import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.paging.LoadState
import com.google.assignment.R
import com.google.assignment.base.BaseFragment
import com.google.assignment.databinding.ListFragmentBinding
import com.google.assignment.db.Cats
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ListFragment : BaseFragment<ListFragmentBinding>(ListFragmentBinding::inflate),
    ListAdapter.AdapterInterface {

    private lateinit var listAdapter: ListAdapter
    private val viewModel: ListViewModel by viewModels()

    override fun onViewCreated() {
        setupViews()
        observers()
    }

    private fun setupViews() {
        listAdapter = ListAdapter(this)

        binding.apply {
            recyclerView.adapter = listAdapter

            swipeRefresh.setOnRefreshListener {
                listAdapter.refresh()
                swipeRefresh.isRefreshing = false
            }

            listAdapter.addLoadStateListener { loadState ->
                loader.isVisible = loadState.refresh is LoadState.Loading
                recyclerView.isVisible = loadState.refresh is LoadState.NotLoading
            }

            recyclerView.adapter = listAdapter.withLoadStateFooter(
                footer = ListLoadStateAdapter(listAdapter)
            )
        }
    }

    private fun observers() {
        viewModel.catsList.observe(viewLifecycleOwner) {
            listAdapter.submitData(lifecycle, it)
        }
    }

    override fun itemClick(result: Cats) {
        sharedViewModel.result = result
        navigateTo(R.id.detailFragment)
    }

}
