package com.github.freitzzz.gameboydb

import android.app.Application
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.di.register

class Application: Application() {
    override fun onCreate() {
        super.onCreate()
        register(vault())
    }
}