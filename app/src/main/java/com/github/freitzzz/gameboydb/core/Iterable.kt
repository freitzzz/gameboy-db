package com.github.freitzzz.gameboydb.core

/**
 * Selects all values that match a condition as specified in the tuple.
 *
 * ```kotlin
 * val value = 45
 * allOf(R.id.res_id to value > 30, R.id.res_id2 to value > 50) // [R.id.res_id]
 * ```
 */
fun <T> allOf(vararg values: Pair<T, Boolean>) = values.filter { it.second }.map { it.first }