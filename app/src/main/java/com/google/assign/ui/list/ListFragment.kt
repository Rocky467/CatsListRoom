package com.google.assign.ui.list

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import com.google.assign.R
import com.google.assign.databinding.ListFragmentBinding
import com.google.assign.model.Cats
import com.google.assign.ui.BaseFragment
import com.google.assign.ui.ListViewModel
import com.google.assign.viewModel.SharedViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.getViewModel

class ListFragment : BaseFragment(), AdapterInterface {

    private lateinit var binding: ListFragmentBinding
    private lateinit var listAdapter: ListAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: ListViewModel by lazy {
        getViewModel()
    }


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

        if (isConnected()) {
            setupRecyclerView()
            observers()
        } else {
            alertDialog(
                getString(R.string.no_internet),
                getString(R.string.no_internet_try),
                { okClick() },
                { cancelClick() })
        }
    }

    private fun okClick() {
        setupRecyclerView()
        observers()
    }

    private fun cancelClick() {
        activity?.finish()
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
            // .mediator or .source decide here
            binding.loader.isVisible = loadState.mediator?.refresh is LoadState.Loading
        }

        binding.recyclerView.adapter = listAdapter.withLoadStateFooter(
            footer = ListLoadStateAdapter(listAdapter)
        )
    }


    private fun observers() {
        with(viewModel) {

            lifecycleScope.launch {
                users.collectLatest {
                    listAdapter.submitData(it)
                }
            }
        }
    }

    override fun itemClick(result: Cats) {
        sharedViewModel.result = result
        findNavController().navigate(R.id.detailFragment)
    }


}
