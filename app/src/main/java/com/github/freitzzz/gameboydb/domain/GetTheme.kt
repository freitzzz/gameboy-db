package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.Theme
import com.github.freitzzz.gameboydb.data.repository.PreferencesRepository

class GetTheme(
    private val repository: PreferencesRepository,
) {
    operator fun invoke(): Theme {
        return repository.theme().orElse {
            Theme(R.style.Theme_GameboyDB)
        }
    }
}