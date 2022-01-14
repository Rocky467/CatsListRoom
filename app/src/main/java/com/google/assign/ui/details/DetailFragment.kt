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
import com.google.assign.viewModel.SharedViewModel

class DetailFragment : BaseFragment() {

    private lateinit var binding: DetailFragmentBinding

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DetailFragmentBinding.inflate(inflater, container, false).apply {
            lifecycleOwner = this@DetailFragment
            user = sharedViewModel.user
        }
        return binding.root
    }

}
