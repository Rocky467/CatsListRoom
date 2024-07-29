package com.google.assignment.base

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.viewbinding.ViewBinding
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.google.assignment.MainActivity
import com.google.assignment.R
import com.google.assignment.utils.SharedViewModel

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
        if (!isConnected()) {
            alertDialogNoInternet()
        }
        onViewCreated()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    @Suppress("DEPRECATION")
    private fun isConnected(): Boolean {
        val cm =
            requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    private fun alertDialogNoInternet() {
        MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = getString(R.string.no_internet))
            message(text = getString(R.string.no_internet_try))
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay")
        }
    }

    fun alertDialog(
        title: String,
        msg: String,
        okClick: () -> Unit,
        cancelClick: (() -> Unit)? = null
    ) {
        MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = title)
            message(text = msg)
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay") { okClick() }
            negativeButton(text = "Cancel") { cancelClick?.invoke() }
        }
    }

    fun navigateTo(fragmentId: Int) {
        findNavController().navigate(fragmentId)
    }

    fun String.setHomeTitle() {
        (requireActivity() as MainActivity).supportActionBar?.title = this
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

}