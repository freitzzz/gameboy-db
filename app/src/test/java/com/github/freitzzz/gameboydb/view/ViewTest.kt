package com.github.freitzzz.gameboydb.view

import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import io.mockk.every
import io.mockk.mockk
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test


class ViewTest {
    @Test
    fun `landscape returns true if orientation is LANDSCAPE`() {
        val context = mockk<Context>()
        val resources = mockk<Resources>()
        val configuration = mockk<Configuration>()

        every { context.resources } returns resources
        every { resources.configuration } returns configuration
        configuration.orientation = Configuration.ORIENTATION_LANDSCAPE

        val landscape = context.landscape()
        assertTrue(landscape)
    }

    @Test
    fun `landscape returns false if orientation is PORTRAIT`() {
        val context = mockk<Context>()
        val resources = mockk<Resources>()
        val configuration = mockk<Configuration>()

        every { context.resources } returns resources
        every { resources.configuration } returns configuration
        configuration.orientation = Configuration.ORIENTATION_PORTRAIT

        val landscape = context.landscape()
        assertFalse(landscape)
    }
}