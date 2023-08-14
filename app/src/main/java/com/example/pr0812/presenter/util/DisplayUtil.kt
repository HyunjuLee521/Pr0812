package com.example.pr0812.presenter.util

import android.content.Context
import android.util.DisplayMetrics
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator




fun getDisplayWidth(context: Context): Int {
    val display: DisplayMetrics = context.resources.displayMetrics
    return display.widthPixels
}

fun getDisplayHeight(context: Context): Int {
    val display: DisplayMetrics = context.resources.displayMetrics
    return display.heightPixels
}

fun dpToPx(context: Context, dp: Int): Int {
    return (dp * context.resources.displayMetrics.density).toInt()
}

fun disableAnimation(recyclerView: RecyclerView) {
    val itemAnimator = recyclerView.itemAnimator as SimpleItemAnimator?
    itemAnimator!!.supportsChangeAnimations = false
}