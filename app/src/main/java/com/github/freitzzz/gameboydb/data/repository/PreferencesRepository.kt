package com.github.freitzzz.gameboydb.data.repository

import android.content.SharedPreferences
import com.github.freitzzz.gameboydb.core.None
import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Option
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.core.Some
import com.github.freitzzz.gameboydb.core.runSafe
import com.github.freitzzz.gameboydb.data.model.Theme

interface PreferencesRepository {
    fun theme(): Option<Theme>
    fun save(theme: Theme): OperationResult<Unit>
}

class SharedPreferencesRepository(
    private val sharedPreferences: SharedPreferences,
) : PreferencesRepository {
    override fun theme(): Option<Theme> {
        return kotlin.runCatching {
            getInt(PreferenceKey.THEME).map { Theme(it) }
        }.getOrElse { None() }
    }

    override fun save(theme: Theme): OperationResult<Unit> {
        return runSafe {
            sharedPreferences.edit().let {
                it.putInt(PreferenceKey.THEME.value, theme.id)
                Right(it.apply())
            }
        }
    }

    private fun getInt(key: PreferenceKey) = with(sharedPreferences.getInt(key.value, -1)) {
        if (this != -1) Some(this) else None()
    }
}

internal enum class PreferenceKey(val value: String) {
    THEME("app.theme"),
    LANGUAGE("app.language")
}