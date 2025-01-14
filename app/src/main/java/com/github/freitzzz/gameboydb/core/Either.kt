package com.github.freitzzz.gameboydb.core

import com.github.freitzzz.gameboydb.core.UnknownError as Unknown

sealed class Either<L, R> {
    fun isLeft() = this is Left
    fun isRight() = this is Right

    fun <B> fold(ifLeft: (l: L) -> B, ifRight: (r: R) -> B): B {
        return when (this) {
            is Left -> ifLeft(this.value)
            is Right -> ifRight(this.value)
        }
    }

    fun unfold(ifLeft: () -> R): R {
        return when (this) {
            is Left -> ifLeft()
            is Right -> this.value
        }
    }

    fun <R2> map(ifRight: (r: R) -> R2): Either<L, R2> {
        return when (this) {
            is Left -> Left(this.value)
            is Right -> Right(ifRight(this.value))
        }
    }

    fun <L2> orElse(ifLeft: (l: L) -> L2): Either<L2, R> {
        return when (this) {
            is Left -> Left(ifLeft(this.value))
            is Right -> Right(this.value)
        }
    }
}

data class Left<L, R>(val value: L) : Either<L, R>()
data class Right<L, R>(val value: R) : Either<L, R>()

typealias OperationResult<R> = Either<OperationError, R>

suspend fun <R> runSafeSuspend(
    call: suspend () -> OperationResult<R>,
): OperationResult<R> {
    return try {
        call()
    } catch (error: Throwable) {
        Left(Unknown(error.toString()))
    }
}

suspend fun <R> runSafeSuspend(
    call: suspend () -> OperationResult<R>,
    onError: ((error: Throwable) -> OperationError)? = null,
): OperationResult<R> {
    return try {
        call()
    } catch (error: Throwable) {
        when (onError) {
            null -> Left(Unknown(error.toString()))
            else -> Left(onError(error))
        }
    }
}
