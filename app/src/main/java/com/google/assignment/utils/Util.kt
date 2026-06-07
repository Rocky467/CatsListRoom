package com.google.assignment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.net.ConnectivityManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.DiffUtil
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.Snackbar
import com.google.assignment.R

object Util {

    fun log(tag: String, msg: Any?) {
        Log.d(tag, "$msg")
    }

    fun toast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun View.showError(message: String) {
        Snackbar.make(this, message, Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun ImageView.loadUrl(url: String?) = Glide.with(context)
        .load(url)
        .placeholder(R.drawable.default_placeholder)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .dontAnimate() // to stop .gif
        .into(this)

    fun View.visible() {
        visibility = VISIBLE
    }

    fun View.gone() {
        visibility = GONE
    }

    fun View.invisible() {
        visibility = INVISIBLE
    }

    fun delay(sec: Long, function: () -> Unit) {
        Handler(Looper.getMainLooper()).postDelayed({
            function()
        }, sec * 1000)
    }

    fun View.onClick(onClick: () -> Unit) {
        setOnClickListener {
            onClick()
        }
    }

    @Suppress("DEPRECATION")
    fun isConnected(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = cm.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnectedOrConnecting
    }

    fun alertDialogNoInternet(context: Context) {
        MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = context.getString(R.string.no_internet))
            message(text = context.getString(R.string.no_internet_try))
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay")
        }
    }

    fun alertDialog(
        context: Context,
        title: String,
        msg: String,
        okClick: () -> Unit,
        cancelClick: (() -> Unit)? = null
    ) {
        MaterialDialog(context, BottomSheet(LayoutMode.WRAP_CONTENT)).show {
            title(text = title)
            message(text = msg)
            cornerRadius(10f)
            cancelable(false)
            positiveButton(text = "Okay") { okClick() }
            negativeButton(text = "Cancel") { cancelClick?.invoke() }
        }
    }

    fun <T : Any> diffUtil(
        areItemsTheSame: (oldItem: T, newItem: T) -> Boolean
    ): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }


    fun <T> loadData(it: Resource<T>, progressBar: ProgressBar): T? {
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

