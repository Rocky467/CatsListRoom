package com.google.assign.ui

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext

abstract class BaseFragment : Fragment(), CoroutineScope {

    private lateinit var job: Job

    override val coroutineContext: CoroutineContext
        get() = job + Dispatchers.Main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        job = Job()
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()
    }

    //for both click
    fun alertDialog(title: String, msg: String, okClick: () -> Unit, cancelClick: () -> Unit) {
        MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = title)
            message(text = msg)
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay") { okClick() }
            negativeButton(text = "Cancel") { cancelClick() }
        }
    }

    //for ok click
    fun alertDialog(title: String, msg: String, okClick: () -> Unit) {
        MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = title)
            message(text = msg)
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay") { okClick() }
        }
    }

    fun isConnected(): Boolean {
        val cm = requireContext().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

}