package com.github.freitzzz.gameboydb.core

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class VaultTest {
    @Test
    fun `if super class is not defined, base class is used to infer dependency`() {
        val vault = Vault()
        val value = "my-dependency"

        vault.store(value)
        assertEquals(value, vault.lookup<String>())
    }

    @Test
    fun `if super class is defined, base class is not used to infer dependency`() {
        val vault = Vault()
        val value = "my-dependency"

        vault.store<Any>(value)
        assertNull(vault.lookup<String>())
    }
}