package com.github.freitzzz.gameboydb.view

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.view.custom.model.Preference
import kotlin.reflect.KClass

/**
 * A list of bundle keys that are known at compile-time for specific types.
 * This list is used in [toBundle] to automatically create a bundle with a value.
 */
val extraKeys = mapOf<KClass<*>, String>(
    Game::class to "game",
    Preference::class to "preference"
)

/**
 * Wrapper around [Context.startActivity] for pushing an activity to the top of the backstack.
 *
 * If you need to pass data to the activity, use the [data] param. This param can either be of an
 * arbitrary type (which will be wrapped around using [toBundle]), or a bundle instance.
 */
inline fun <reified A : Activity> Context.navigateTo(data: Any? = null) {
    this.startActivity(
        Intent(this, A::class.java).apply {
            when (data) {
                null -> return@apply
                is Bundle -> putExtras(data)
                else -> putExtras(data.toBundle())
            }
        }
    )
}

/**
 * Wrapper around [Activity.finish] for popping the current activity from the top of the backstack.
 */
fun Activity.popBack() = finish()

/**
 * Retrieves data passed to an activity using the [Intent] that was passed on [Context.startActivity].
 *
 * This method only works if you have launched the activity using [navigateTo] or used [toBundle]
 * to wrap data in a bundle.
 */
inline fun <reified T : Any> Intent.get(): T? {
    val key = extraKeys[T::class] ?: T::class.java.name
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        return this.getParcelableExtra(key, T::class.java)
    }

    @Suppress("DEPRECATION")
    return this.getParcelableExtra(key) as T?
}

/**
 * Wraps this value in a bundle. See [extraKey] to learn how the key is selected to reference the value.
 */
fun Any.toBundle() = bundleOf(extraKey(this) to this)

/**
 * Selects a key to wrap data in a bundle. If the data type was registered in [extraKeys], it uses
 * that key, else it will use the type java class name as the key (e.g., `extraKey("data") => "kotlin.String"`)
 */
private fun extraKey(data: Any) = extraKeys[data::class] ?: data::class.java.name

/**
 * Direct wrapper around [androidx.core.os.bundleOf] so that the function can be easily mocked in unit tests.
 */
fun bundleOf(vararg pairs: Pair<String, Any?>) = androidx.core.os.bundleOf(*pairs)