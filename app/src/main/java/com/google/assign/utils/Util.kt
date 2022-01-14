package com.google.assign.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide


fun log(msg: String) {
    Log.d("dataHere", msg)
}

fun log(tag: String, msg: String) {
    Log.d(tag, msg)
}

@BindingAdapter("android:loadUrl")
fun loadUrl(view: ImageView, url: String) {
    view.load(url)
}

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .into(this)
}