package com.google.assignment.utils

import android.annotation.SuppressLint
import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.INVISIBLE
import android.view.View.VISIBLE
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import com.google.android.material.snackbar.Snackbar

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
        Snackbar.make(this, message, Snackbar.LENGTH_LONG)
            .setAction("Action", null).show()
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


    fun <T : Any> diffUtil(
        areItemsTheSame: (oldItem: T, newItem: T) -> Boolean
    ): DiffUtil.ItemCallback<T> = object : DiffUtil.ItemCallback<T>() {
        override fun areItemsTheSame(oldItem: T, newItem: T): Boolean =
            areItemsTheSame(oldItem, newItem)

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: T, newItem: T): Boolean =
            oldItem == newItem
    }

}

