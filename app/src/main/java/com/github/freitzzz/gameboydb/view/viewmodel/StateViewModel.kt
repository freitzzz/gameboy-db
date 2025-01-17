package com.github.freitzzz.gameboydb.view.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * A view model that holds a state along its lifecycle. State is mutated using the [emit] method.
 * Initial states can be passed using the general-purpose [StateViewModelFactory].
 *
 * ```kotlin
 * class AuthenticationStateViewModel: StateViewModel<AuthenticationState>(state) {
 *      fun login(credentials: Credentials) {
 *          emit(AuthenticationInProgress())
 *
 *          val code = login()
 *          when (code) {
 *              200 -> emit(AuthenticationSuccess())
 *              401 -> emit(AuthenticationFailure())
 *          }
 *      }
 * }
 * ```
 */
abstract class StateViewModel<S>(
    initialState: S,
) : ViewModel() {
    var state: S = initialState
        private set

    val liveState: LiveData<S> by lazy { MutableLiveData(initialState) }

    /**
     * Emits a new state.
     * Mutates [state] property and dispatches the state in [liveState].
     */
    fun emit(state: S) = apply {
        this.state = state
        (this.liveState as MutableLiveData<S>).postValue(state)
    }
}

/**
 * A general purpose [ViewModelProvider.Factory] for creating instances of [StateViewModel].
 *
 * ```kotlin
 * class CounterViewModel(initial: Int): StateViewModel<Int>(initial)
 * ...
 *
 * val viewModel = ViewModelProvider(this, StateViewModelFactory(0))[CounterViewModel::class.java]
 * ```
 */
class StateViewModelFactory<P>(
    private val data: P,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(data!!::class.java).newInstance(data)
    }
}