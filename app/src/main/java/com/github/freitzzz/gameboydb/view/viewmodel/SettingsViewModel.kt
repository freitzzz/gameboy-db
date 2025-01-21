package com.github.freitzzz.gameboydb.view.viewmodel

import android.content.Context
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.github.freitzzz.gameboydb.BuildConfig
import com.github.freitzzz.gameboydb.view.openExternal

class SettingsViewModel : ViewModel() {
    fun openProjectRepository(context: Context) = context.openExternal(
        Uri.parse(BuildConfig.REPOSITORY_URL)
    )

    fun appVersion() = BuildConfig.VERSION_NAME
}