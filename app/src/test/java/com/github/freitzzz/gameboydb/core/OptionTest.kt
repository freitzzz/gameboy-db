package com.github.freitzzz.gameboydb.core

import org.junit.Assert.assertEquals
import org.junit.Test

class OptionTest {

    @Test
    fun `if value is present, orElse callback is not called`() {
        val value = 1
        val option = Some(value)

        val result = option.orElse { 2 }
        assertEquals(value, result)
    }

    @Test
    fun `if value is not present, orElse callback is called`() {
        val value = 2
        val option = None<Int>()

        val result = option.orElse { value }
        assertEquals(2, result)
    }
}