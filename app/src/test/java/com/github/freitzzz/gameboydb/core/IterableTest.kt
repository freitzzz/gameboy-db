package com.github.freitzzz.gameboydb.core

import org.junit.Assert.assertEquals
import org.junit.Test

class IterableTest {
    @Test
    fun `allOf selects only the values which the right side of the tuple is true`() {
        val max = 3
        val tuples = (1..5).map { it to (it <= max) }.toTypedArray()

        val values = allOf(*tuples)
        assertEquals(listOf(1, 2, 3), values)
    }
}