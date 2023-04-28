package com.google.assign.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.*
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import retrofit2.Call


@SuppressLint("StaticFieldLeak")
object Util {

    lateinit var context: Context

    fun log(tag: String, msg: Any?) {
        Log.d(tag, "$msg")
    }

    fun toast(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun View.showError(message: String) {
        Snackbar.make(this, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show()
    }

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

    fun <T> validateApi(apiCall: Call<T>): Resource<T> {
        return when (val res = ApiResponse.create(apiCall)) {
            is ApiResponse.ApiSuccessResponse -> Resource.success(res.data)
            is ApiResponse.ApiSuccessEmptyResponse -> Resource.success(null)
            is ApiResponse.ApiErrorResponse -> Resource.error(res.errorMessage, null)
            is ApiResponse.ApiSuccessEmptyResponseWithHeaders -> Resource.success(null, res.headers)
        }
    }

}

