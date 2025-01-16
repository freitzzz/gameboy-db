package com.github.freitzzz.gameboydb.core

import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import io.mockk.verify
import com.github.freitzzz.gameboydb.core.UnknownError as Unknown
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class EitherTest {
    @Test
    fun `runSafeSuspend returns Left of UnknownError if call throws and onError is not passed`() =
        runTest {
            val throwable = Exception("something went wrong")
            val unknown = Unknown(throwable.toString())

            val result = runSafeSuspend<Nothing>(call = { throw throwable })
            assertEquals(Left<OperationError, Nothing>(unknown), result)
        }

    @Test
    fun `runSafeSuspend uses onError result if call is passed`() = runTest {
        val throwable = Exception("something went wrong")
        val error = NoInternetConnectionError("turn on wifi!!")

        val result = runSafeSuspend<Nothing>(call = { throw throwable }) { error }
        assertEquals(Left<OperationError, Nothing>(error), result)
    }

    @Test
    fun `runSafeSuspend returns call result if nothing is thrown`() = runTest {
        val error = NoInternetConnectionError("turn on wifi!!")

        val result = runSafeSuspend(call = { Right("wifi is working!!") }) { error }
        assertEquals(Right<OperationError, String>("wifi is working!!"), result)
    }

    @Test
    fun `each calls callback if either is right handed`() {
        val callback = mockk<(String) -> Unit>()
        val value = "result data"
        val either = Right<OperationError, String>(value)

        every { callback.invoke(any()) } just runs

        either.each(callback)
        verify { callback(value) }
    }

    @Test
    fun `each does not call callback if either is left handed`() {
        val callback = mockk<(String) -> Unit>()
        val value = NoInternetConnectionError("turn on wifi!!")
        val either = Left<OperationError, String>(value)

        every { callback.invoke(any()) } just runs

        either.each(callback)
        verify(exactly = 0) { callback(any()) }
    }
}