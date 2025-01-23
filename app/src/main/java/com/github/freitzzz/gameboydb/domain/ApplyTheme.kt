package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.model.Theme
import com.github.freitzzz.gameboydb.data.repository.PreferencesRepository
import com.github.freitzzz.gameboydb.domain.state.PreferenceUpdates
import com.github.freitzzz.gameboydb.domain.state.ThemeChanged

// todo: add tests
class ApplyTheme(
    private val repository: PreferencesRepository,
    private val updates: PreferenceUpdates,
) {
    operator fun invoke(theme: Theme) {
        return repository.save(theme).each {
            updates.tryEmit(
                ThemeChanged(theme)
            )
        }
    }
}