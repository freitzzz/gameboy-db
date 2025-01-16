package com.github.freitzzz.gameboydb.navigation

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.github.freitzzz.gameboydb.activity.MainActivity
import com.github.freitzzz.gameboydb.data.model.Game
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkConstructor
import io.mockk.mockkStatic
import io.mockk.runs
import io.mockk.verify
import io.mockk.verifyAll
import org.junit.Test

class NavigationTest {

    init {
        mockkStatic(::bundleOf)
        mockkConstructor(Intent::class)
    }

    @Test
    fun `toBundle uses known key for putting GameTile in extras`() {
        val key = "game-tile"
        val data = mockk<Game>()
        every { bundleOf(key to data) } returns mockk()

        data.toBundle()
        verify(exactly = 1) { bundleOf(key to data) }
    }

    @Test
    fun `toBundle uses resolved java class name if key is not known for input data`() {
        val key = String::class.java.name
        val data = "my bundled data"

        every { bundleOf(key to data) } returns mockk()

        data.toBundle()
        verify(exactly = 1) { bundleOf(key to data) }
    }

    @Test
    fun `navigateTo calls startActivity and doesn't apply a bundle if data is not passed`() {
        val context = mockk<Context>()

        every { context.startActivity(any()) } just runs

        context.navigateTo<MainActivity>()
        verify(exactly = 1) { context.startActivity(any()) }
    }

    @Test
    fun `navigateTo calls startActivity and applies bundle directly if a bundle is passed as data`() {
        val context = mockk<Context>()
        val bundle = mockk<Bundle>()

        every { context.startActivity(any()) } just runs
        every { anyConstructed<Intent>().putExtras(bundle) } returns mockk()

        context.navigateTo<MainActivity>(data = bundle)
        verifyAll {
            context.startActivity(any())
            anyConstructed<Intent>().putExtras(bundle)
        }
    }

    @Test
    fun `navigateTo calls startActivity and applies bundle created from toBundle if a specific type is passed as data`() {
        val context = mockk<Context>()
        val data = mockk<Game>()
        val bundle = mockk<Bundle>()

        every { context.startActivity(any()) } just runs
        every { anyConstructed<Intent>().putExtras(bundle) } returns mockk()
        every { bundleOf(any()) } returns bundle

        context.navigateTo<MainActivity>(data = data)
        verifyAll {
            context.startActivity(any())
            anyConstructed<Intent>().putExtras(bundle)
        }
    }
}