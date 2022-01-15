package com.google.assign.ui.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.assign.databinding.DetailFragmentBinding
import com.google.assign.ui.BaseFragment
import com.google.assign.ui.ListViewModel
import com.google.assign.utils.load
import com.google.assign.utils.log
import com.google.assign.viewModel.SharedViewModel
import org.koin.androidx.viewmodel.ext.android.getViewModel

class DetailFragment : BaseFragment() {

    private lateinit var binding: DetailFragmentBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val viewModel: ListViewModel by lazy {
        getViewModel()
    }
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
        with(viewModel) {
            getCatById(catId)
            getCatById.observe(viewLifecycleOwner, {
                it?.let {
                    log("catHere", it.toString())
                    binding.cats = it
                }
            })
        }
    }
}
