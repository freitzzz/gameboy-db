package com.github.freitzzz.gameboydb.core

import java.lang.reflect.Type

sealed class TypedVault<T> {
    val container = mutableMapOf<Type, T>()

    inline fun <reified S: T> store(value: S) {
        container[S::class.java] = value
    }

    inline fun <reified S: T> lookup(): S? = container[S::class.java] as S?
    inline fun <reified S: T> get() = lookup<S>()!!
}

class Vault: TypedVault<Any>()

private val vault = Vault()
fun vault() = vault