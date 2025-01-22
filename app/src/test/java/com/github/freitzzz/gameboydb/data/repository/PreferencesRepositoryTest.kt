package com.github.freitzzz.gameboydb.data.repository

import android.content.SharedPreferences
import com.github.freitzzz.gameboydb.core.Left
import com.github.freitzzz.gameboydb.core.None
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.core.Some
import com.github.freitzzz.gameboydb.data.model.Theme
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.runs
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class SharedPreferencesRepositoryTest {
    private val sharedPreferences = mockk<SharedPreferences>()
    private val sharedPreferencesEditor = mockk<SharedPreferences.Editor>()

    @Test
    fun `theme returns none if shared preferences does not contain a value for theme key`() {
        val default = -1
        val repository = SharedPreferencesRepository(sharedPreferences)

        every { sharedPreferences.getInt(PreferenceKey.THEME.value, default) } returns default

        val result = repository.theme()
        assertEquals(None<Theme>(), result)
    }

    @Test
    fun `theme returns none if shared preferences call throws exception`() {
        val default = -1
        val exception = Exception("disk failure!!")
        val repository = SharedPreferencesRepository(sharedPreferences)

        every { sharedPreferences.getInt(PreferenceKey.THEME.value, default) } throws exception

        val result = repository.theme()
        assertEquals(None<Theme>(), result)
    }

    @Test
    fun `theme returns some with theme if shared preferences contains a value for theme key`() {
        val default = -1
        val value = 1
        val repository = SharedPreferencesRepository(sharedPreferences)


        every { sharedPreferences.getInt(PreferenceKey.THEME.value, default) } returns value

        val result = repository.theme()
        assertEquals(Some(Theme(value, "")), result)
    }

    @Test
    fun `save returns left if shared preferences editor throws exception`() {
        val theme = Theme(0, "")
        val exception = Exception("disk failure!!")
        val repository = SharedPreferencesRepository(sharedPreferences)

        every { sharedPreferencesEditor.apply() } throws exception
        every { sharedPreferences.edit() } returns sharedPreferencesEditor

        val result = repository.save(theme)
        assertTrue(result is Left)
    }

    @Test
    fun `save returns right if shared preferences editor does not throw exception`() {
        val theme = Theme(0, "")
        val repository = SharedPreferencesRepository(sharedPreferences)

        every {
            sharedPreferencesEditor.putInt(PreferenceKey.THEME.value, theme.id)
        } returns sharedPreferencesEditor
        every { sharedPreferencesEditor.apply() } just runs
        every { sharedPreferences.edit() } returns sharedPreferencesEditor

        val result = repository.save(theme)
        assertTrue(result is Right)
    }
}