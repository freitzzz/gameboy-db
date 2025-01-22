package com.github.freitzzz.gameboydb.domain

import android.content.Context
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.Theme

class GetAvailableThemes {
    operator fun invoke(context: Context) = with(context) {
        listOf(
            R.string.theme_system,
            R.string.theme_day,
            R.string.theme_night,
        ).map { Theme(it, getString(it)) }
    }
}