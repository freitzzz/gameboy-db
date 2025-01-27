package com.github.freitzzz.gameboydb.domain

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings

class OpenLanguageSettings {
    operator fun invoke(context: Context): Unit {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            context.startActivity(
                Intent(
                    Settings.ACTION_APP_LOCALE_SETTINGS,
                    Uri.parse("package:${context.packageName}")
                )
            )
        } else {
            context.startActivity(
                Intent(
                    Settings.ACTION_LOCALE_SETTINGS
                )
            )
        }
    }
}