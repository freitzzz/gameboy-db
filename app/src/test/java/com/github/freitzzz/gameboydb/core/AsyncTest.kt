package com.github.freitzzz.gameboydb.core

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class AsyncTest {
    @Test
    fun `onIO does not block current coroutine scope`() = runTest {
        var value = 0
        awaitAll(async { value-- })

        onIO { value++ }
        assertEquals(-1, value)
    }
}