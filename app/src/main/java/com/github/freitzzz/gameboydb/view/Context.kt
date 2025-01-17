package com.github.freitzzz.gameboydb.view

import android.content.Context
import android.content.res.Configuration
import android.os.Build
import android.util.DisplayMetrics
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat

/**
 * Resolves a [android.graphics.drawable.Drawable] instance by resource id.
 */
fun Context.drawable(@DrawableRes id: Int) = ContextCompat.getDrawable(this, id)

/**
 * Checks if the device is in landscape ("horizontal") mode.
 */
fun Context.landscape() = resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE

/**
 * Resolves a [DisplayMetrics] instance for the current device configuration.
 */
fun Context.displayMetrics() = ContextCompat.getDisplayOrDefault(this).let {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        createDisplayContext(it).resources.displayMetrics!!
    } else {
        @Suppress("DEPRECATION")
        DisplayMetrics().also { dm -> it.getMetrics(dm) }
    }
}