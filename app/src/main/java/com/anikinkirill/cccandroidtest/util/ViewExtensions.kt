package com.anikinkirill.cccandroidtest.util

import android.content.Context
import android.view.View
import android.widget.ProgressBar

fun Context.showProgressBar(isVisible: Boolean, progressBar: ProgressBar) {
    if(isVisible) {
        progressBar.visibility = View.VISIBLE
    }else {
        progressBar.visibility = View.GONE
    }
}