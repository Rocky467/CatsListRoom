package com.google.assignment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.google.assignment.MainActivity
import com.google.assignment.utils.Resource
import com.google.assignment.utils.SharedViewModel
import com.google.assignment.utils.Util.showError

abstract class BaseFragment<VB : ViewBinding>(
    private val inflate: (LayoutInflater, ViewGroup?, Boolean) -> VB
) : Fragment() {

    private var _binding: VB? = null
    val binding: VB get() = _binding!!

    val sharedViewModel: SharedViewModel by activityViewModels()

    open fun onCreateView() {}
    open fun onViewCreated() {}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = inflate.invoke(inflater, container, false)
        onCreateView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun navigateTo(fragmentId: Int) {
        findNavController().navigate(fragmentId)
    }

    fun String.setHomeTitle() {
        (requireActivity() as MainActivity).supportActionBar?.title = this
    }

    fun <T> takeIfSuccess(it: Resource<T>, progressBar: ProgressBar): T? {
        progressBar.isVisible = false
        when (it) {
            is Resource.Loading -> {
                progressBar.isVisible = true
            }

            is Resource.Success -> {
                return it.data
            }

            is Resource.Error -> {
                progressBar.showError(it.error.toString())
            }
        }
        return null
    }

}