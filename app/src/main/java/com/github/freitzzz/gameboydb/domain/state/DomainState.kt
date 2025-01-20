package com.github.freitzzz.gameboydb.domain.state

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * A base class for defining the state of a domain topic.
 */
abstract class DomainState<T> : Flow<T> {
    private val inner by lazy { MutableSharedFlow<T>(replay = 1) }

    override suspend fun collect(collector: FlowCollector<T>) = inner.collect(collector)

    fun tryEmit(value: T) = inner.tryEmit(value)
}