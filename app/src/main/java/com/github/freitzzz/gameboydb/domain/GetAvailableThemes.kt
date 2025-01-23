package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.Theme

class GetAvailableThemes {
    operator fun invoke() = listOf(
        R.style.Theme_GameboyDB,
        R.style.Theme_GameboyDB_Day,
        R.style.Theme_GameboyDB_Night,
        R.style.Theme_GameboyDB_Orange,
    ).map { Theme(it) }
}