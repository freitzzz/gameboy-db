package com.github.freitzzz.gameboydb.core

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 * Executes [block] on a IO [kotlin.coroutines.CoroutineContext] without blocking the current scope.
 *
 * Only use this function for calls that fit input/output operations (e.g., network request, file io).
 */
fun CoroutineScope.onIO(block: suspend () -> Unit) = launch(Dispatchers.IO) { block() }

/**
 * Executes [block] on a IO [kotlin.coroutines.CoroutineContext] by blocking the current scope.
 *
 * Only use this function for calls that fit input/output operations (e.g., network request, file io).
 */
suspend fun <R> withIO(block: suspend () -> R) = withContext(Dispatchers.IO) {
    block()
}