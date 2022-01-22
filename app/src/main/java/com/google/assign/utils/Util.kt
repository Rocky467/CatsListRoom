package com.google.assign.utils

import android.util.Log
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.assign.R
import retrofit2.Call


fun log(tag: String, msg: Any) {
    Log.d(tag, "$msg")
}

@BindingAdapter("android:loadUrl")
fun loadUrl(view: ImageView, url: String?) {
    if (url != null) {
        view.load(url)
    }
}

fun ImageView.load(url: String) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.default_placeholder)
        .centerCrop()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}

fun <T> validateApi(apiCall: Call<T>): Resource<T> {
    return when (val res = ApiResponse.create(apiCall)) {
        is ApiSuccessResponse -> Resource.success(res.data)
        is ApiSuccessEmptyResponse -> Resource.success(null)
        is ApiErrorResponse -> Resource.error(res.errorMessage, null)
        is ApiSuccessEmptyResponseWithHeaders -> Resource.success(null, res.headers)
    }
}
