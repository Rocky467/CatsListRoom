package com.google.assignment.ui.details

import com.google.assignment.base.BaseFragment
import com.google.assignment.databinding.DetailFragmentBinding
import com.google.assignment.ui.list.ListViewModel
import com.google.assignment.utils.Resource
import com.google.assignment.utils.Util.showError
import org.koin.androidx.viewmodel.ext.android.viewModel

class DetailFragment : BaseFragment<DetailFragmentBinding>(DetailFragmentBinding::inflate) {

    private lateinit var catId: String
    private val listViewModel: ListViewModel by viewModel()

    override fun onCreateView() {
        binding.lifecycleOwner = this@DetailFragment
    }

    override fun onViewCreated() {
        catId = sharedViewModel.result.id
        observers()
    }

    private fun observers() {
        listViewModel.getCatById(catId)
        listViewModel.getCatById.observe(viewLifecycleOwner) {
            when (it) {
                is Resource.Loading -> {
                }

                is Resource.Success -> {
                    binding.cats = it.data
                }

                is Resource.Error -> {
                    binding.root.showError(it.error.toString())
                }
            }
        }
    }

}
