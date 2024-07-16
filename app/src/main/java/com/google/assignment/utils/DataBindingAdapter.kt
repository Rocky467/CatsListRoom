package com.google.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.assignment.R

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("android:loadUrl")
    fun loadUrl(view: ImageView, url: String?) {
        url?.let {
            view.load(it)
        }
    }

    private fun ImageView.load(url: String) {
        Glide.with(context)
            .load(url)
            .placeholder(R.drawable.default_placeholder)
            .centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(this)
    }
}