package com.github.freitzzz.gameboydb.view

import android.app.Activity
import android.view.View
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

/**
 * Resolves zero, one or more [View] instances for a set of resource id.
 */
fun Activity.withRes(@IdRes vararg ids: Int) = ids.map { view(it) }

/**
 * Resolves a list of [View] instances for a list of resource id.
 */
fun Activity.withRes(@IdRes ids: List<Int>) = ids.map { view(it) }

/**
 * Shows a view by setting its visibility as [View.VISIBLE].
 */
fun View.show() {
    visibility = View.VISIBLE
}

/**
 * Hides a view by setting its visibility as [View.INVISIBLE].
 */
fun View.hide() {
    visibility = View.INVISIBLE
}

/**
 * Removes a view from the current layout by setting its visibility as [View.GONE].
 */
fun View.remove() {
    visibility = View.GONE
}

/**
 * Resolves a [View] instance by resource id.
 */
fun View.view(@IdRes id: Int) = viewOf<View>(id)

/**
 * Resolves a [V] instance by resource id.
 */
fun <V : View> View.viewOf(@IdRes id: Int) = findViewById<V>(id)!!

/**
 * Resolves a [View] instance by resource id.
 */
fun Activity.view(@IdRes id: Int) = viewOf<View>(id)

/**
 * Resolves a [V] instance by resource id.
 */
fun <V : View> Activity.viewOf(@IdRes id: Int) = findViewById<V>(id)!!

/**
 * Calls [TextView.setText] for each pair of (ResourceId, String).
 */
fun Activity.setText(vararg pair: Pair<Int, String?>) {
    pair.onEach { viewOf<TextView>(it.first).text = it.second }
}

/**
 * Calls [View.show] for each [View] instance.
 */
fun List<View>.show() = onEach { it.show() }

/**
 * Resolves the [T] instance that is attached to the current activity.
 * Calling this method from a [androidx.fragment.app.Fragment] will always create a new [T] instance.
 */
inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel() =
    ViewModelProvider(this)[T::class.java]