package com.github.freitzzz.gameboydb.view.viewmodel

import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

/**
 * Possible events that lead to a value change.
 */
enum class ValueChangeEvent {
    INSERT,
    DELETE,
}

/**
 * Alias for the callback signature that consumes use to observe values that have changed.
 */
typealias ValueChangedCallback<T> = ValueChangeEvent.(T) -> Unit

/**
 * Interface for observing values changes.
 */
interface ValueObserver<T> {
    fun observe(block: ValueChangedCallback<T>)
}

/**
 * Interface for notifying value changes.
 */
interface ValueNotifier<T> : ValueObserver<T> {
    fun update(value: T, event: ValueChangeEvent)
}

/**
 * Combines [LiveData] with [ValueNotifier].
 */
class LiveDataValueNotifier<T>(
    value: T,
) : ValueNotifier<T>, MutableLiveData<T>(value) {
    private val observers = arrayListOf<ValueChangedCallback<T>>()

    override fun observe(block: ValueChangedCallback<T>) {
        observers.add(block)
    }

    fun observe(owner: LifecycleOwner, block: ValueChangedCallback<T>) {
        observers.add(block)

        super.observe(owner){
            block(ValueChangeEvent.INSERT, it)
        }
    }

    override fun update(value: T, event: ValueChangeEvent) {
        observers.onEach { it(event, value) }
    }

    fun clear() = observers.clear()
}