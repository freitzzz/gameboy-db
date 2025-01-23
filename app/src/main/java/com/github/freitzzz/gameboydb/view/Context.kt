package com.github.freitzzz.gameboydb.view

import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.util.DisplayMetrics
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.annotation.StyleRes
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import com.github.freitzzz.gameboydb.R

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

/**
 * Opens an external link (network/file) using the system preferred app.
 */
fun Context.openExternal(uri: Uri) = this.startActivity(Intent(Intent.ACTION_VIEW, uri))

/**
 * Displays a toast for a short duration.
 */
fun Context.showToast(@StringRes id: Int) = Toast.makeText(this, id, Toast.LENGTH_SHORT).show()

/**
 * Applies a theme using [Context.setTheme] if theme is not system/day/night, or reconfigures the
 * app to follow the system/day/night theme.
 */

// todo: test
fun Context.applyTheme(@StyleRes id: Int) {
    when (id) {
        R.style.Theme_GameboyDB -> if (!isOnSystemTheme()) AppCompatDelegate.setDefaultNightMode(
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        )

        R.style.Theme_GameboyDB_Day -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        R.style.Theme_GameboyDB_Night -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            .also { setTheme(id) }
    }
}

/**
 * Checks if the app is reacting to system theme changes.
 */

private fun isOnSystemTheme() = AppCompatDelegate.getDefaultNightMode().let {
    it == AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM || it == AppCompatDelegate.MODE_NIGHT_UNSPECIFIED
}