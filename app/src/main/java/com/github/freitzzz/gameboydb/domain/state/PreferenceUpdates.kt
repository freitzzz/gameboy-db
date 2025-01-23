package com.github.freitzzz.gameboydb.domain.state

import com.github.freitzzz.gameboydb.data.model.Theme

class PreferenceUpdates : DomainState<PreferenceState>()

sealed class PreferenceState
data class ThemeChanged(val value: Theme) : PreferenceState()