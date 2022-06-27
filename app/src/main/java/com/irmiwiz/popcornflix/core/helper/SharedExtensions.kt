package com.irmiwiz.popcornflix.core.helper

import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

fun <T> MutableLiveData<T>.asLiveData() = this as LiveData<T>

fun View.toggleVisibility(show: Boolean) {
    this.visibility = if (show) View.VISIBLE else View.GONE
}