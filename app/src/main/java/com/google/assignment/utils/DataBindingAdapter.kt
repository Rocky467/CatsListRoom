package com.google.assignment.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.assignment.R

object DataBindingAdapter {

    @JvmStatic
    @BindingAdapter("loadUrl")
    fun ImageView.loadUrl(url: String?) {
        url?.let {
            Glide.with(context)
                .load(it)
                .placeholder(R.drawable.default_placeholder)
                .centerCrop()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
    }
}