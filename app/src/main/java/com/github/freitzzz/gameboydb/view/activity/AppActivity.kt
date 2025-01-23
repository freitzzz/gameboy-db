package com.github.freitzzz.gameboydb.view.activity

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import com.github.freitzzz.gameboydb.view.applyTheme
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewmodel.SettingsViewModel

/**
 * Base activity that all application activities should extend. Handles theme/language changes in
 * background and recreates the activity to apply such changes.
 */
abstract class AppActivity(@LayoutRes layoutId: Int) : AppCompatActivity(layoutId) {
    private val viewModel by lazy { viewModel<SettingsViewModel>() }
    private val themeChanges by lazy { viewModel.themeChanges() }

    override fun onCreate(savedInstanceState: Bundle?) {
        val theme = viewModel.theme()
        this.applyTheme(theme.id)

        themeChanges.observe(this) {
            if (it != theme) {
                // todo: replace with log
                println("theme changed, recreating activity")
                recreate()
            }
        }

        super.onCreate(savedInstanceState)
    }
}