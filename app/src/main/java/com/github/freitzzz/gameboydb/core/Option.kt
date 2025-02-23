package com.github.freitzzz.gameboydb.core

sealed class Option<T> {
    inline fun <R> map(block: (T) -> R) = when (this) {
        is Some -> Some(block(this.value))
        else -> None()
    }

    inline fun orElse(default: () -> T) = when (this) {
        is Some -> value
        else -> default()
    }

    fun present() = this is Some
    fun value() = (this as Some).value
}

data class Some<T>(
    val value: T,
) : Option<T>()

class None<T> : Option<T>() {
    override fun equals(other: Any?) = other is None<*>
    override fun hashCode() = javaClass.hashCode()
}