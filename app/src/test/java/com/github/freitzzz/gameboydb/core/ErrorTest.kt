package com.github.freitzzz.gameboydb.core

import org.junit.Assert.assertEquals
import org.junit.Test

class ErrorTest {
    @Test
    fun `wrap 'wraps' both error messages with a new line`() {
        val error1 = NoInternetConnectionError("turn on wifi!!")
        val error2 = UnknownError("what just happened??")

        val error3 = error1.wrap(error2)
        assertEquals("$error2\n$error1", error3.message)
    }
}