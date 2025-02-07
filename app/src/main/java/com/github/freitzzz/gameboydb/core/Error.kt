package com.github.freitzzz.gameboydb.core

abstract class OperationError(val message: String) {
    fun wrap(error: OperationError) = WrappedError(error, this)

    override fun toString() = message
    override fun hashCode() = message.hashCode()
    override fun equals(other: Any?) = other?.hashCode() == hashCode()
}

class WrappedError(vararg errors: OperationError) :
    OperationError(errors.joinToString("\n") { it.message })

class TimeoutError(message: String) : OperationError(message)
class NoInternetConnectionError(message: String) : OperationError(message)
class UnknownError(message: String) : OperationError(message)
class DownloadError(message: String) : OperationError(message)
class ExpectedJsonResponseError(message: String): OperationError(message)