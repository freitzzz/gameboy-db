package com.github.freitzzz.gameboydb.view.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.BuildConfig
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Theme
import com.github.freitzzz.gameboydb.domain.ApplyTheme
import com.github.freitzzz.gameboydb.domain.GetAvailableThemes
import com.github.freitzzz.gameboydb.domain.GetTheme
import com.github.freitzzz.gameboydb.domain.state.PreferenceUpdates
import com.github.freitzzz.gameboydb.domain.state.ThemeChanged
import com.github.freitzzz.gameboydb.view.openExternal
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.shareIn

class SettingsViewModel : ViewModel() {
    private val applyTheme by lazy { vault().get<ApplyTheme>() }
    private val getTheme by lazy { vault().get<GetTheme>() }
    private val getAvailableThemes by lazy { vault().get<GetAvailableThemes>() }
    private val preferenceUpdates by lazy { vault().get<PreferenceUpdates>() }

    private val themeChanges: LiveData<Theme> by lazy {
        MutableLiveData(theme()).apply {
            preferenceUpdates
                .filter { it is ThemeChanged && it.value != value }
                .onEach { postValue((it as ThemeChanged).value) }
                .shareIn(viewModelScope, SharingStarted.Eagerly)
        }
    }

    fun themeChanges() = themeChanges

    fun openProjectRepository(context: Context) = context.openExternal(
        Uri.parse(BuildConfig.REPOSITORY_URL)
    )

    fun appVersion() = BuildConfig.VERSION_NAME
    fun theme() = getTheme()
    fun availableThemes() = getAvailableThemes()
    fun setTheme(theme: Theme) = this.applyTheme(theme)
}