package com.google.assign.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.assign.databinding.DetailFragmentBinding
import com.google.assign.ui.BaseFragment
import com.google.assign.utils.log

class DetailFragment : BaseFragment() {

    private lateinit var binding: DetailFragmentBinding
    private lateinit var catId: String

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailFragment
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        catId = sharedViewModel.result.id
        observers()
    }

    private fun observers() {
        with(listViewModel) {
            getCatById(catId)
            getCatById.observe(viewLifecycleOwner, {
                it?.let {
                    binding.cats = it
                }
            })
        }
    }

}
