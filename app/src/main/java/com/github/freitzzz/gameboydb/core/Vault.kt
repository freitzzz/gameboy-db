package com.github.freitzzz.gameboydb.core

import java.lang.reflect.Type
import kotlin.reflect.KProperty

sealed class TypedVault<T> {
    val container = mutableMapOf<Type, T>()

    inline fun <reified S: T> store(value: S) {
        container[S::class.java] = value
    }

    inline fun <reified S: T> lookup(): S? = container[S::class.java] as S?
    inline fun <reified S: T> get() = lookup<S>()!!
}

class Vault: TypedVault<Any>() {
    inline operator fun<T, reified V: Any> getValue(nothing: T?, property: KProperty<*>): V {
        return vault().get<V>()
    }
}

private val vault = Vault()
fun vault() = vault