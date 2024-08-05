package com.google.assignment.ui.details

import com.google.assignment.base.BaseFragment
import com.google.assignment.databinding.DetailFragmentBinding
import com.google.assignment.ui.list.ListViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate) {

    private val viewModel: ListViewModel by viewModel()

    override fun onCreateView() {
        binding.lifecycleOwner = this@DetailFragment
    }

    override fun onViewCreated() {
        viewModel.getCatById(sharedViewModel.result.id)
        viewModel.getCatById.observe(viewLifecycleOwner) {
            val data = fetchData(it, binding.loader)
            binding.cats = data
        }
    }

}
