package com.github.freitzzz.gameboydb.view.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.Theme
import com.github.freitzzz.gameboydb.view.custom.PreferenceDialog
import com.github.freitzzz.gameboydb.view.custom.ValuesPreference
import com.github.freitzzz.gameboydb.view.custom.model.PreferenceOptions
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewOf
import com.github.freitzzz.gameboydb.view.viewmodel.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    private val viewModel by lazy { requireActivity().viewModel<SettingsViewModel>() }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.applyLanguagePreference()
        view.applyThemePreference()

        view.view(R.id.settings_star_button).setOnClickListener {
            viewModel.openProjectRepository(view.context)
        }

        view.viewOf<ValuesPreference>(R.id.settings_preference_app_version).value =
            viewModel.appVersion()
    }

    private fun View.applyLanguagePreference() {
        viewOf<ValuesPreference>(R.id.settings_preference_language).apply {
            setOnClickListener {
                viewModel.openAppLanguageSettings(context)
            }
        }
    }

    private fun View.applyThemePreference() {
        val theme = viewModel.theme()
        val availableThemes = viewModel.availableThemes()
        val themeOptions = availableThemes.map { context.getString(it.stringRes()) }

        viewOf<ValuesPreference>(R.id.settings_preference_theme).apply {
            value = theme.toString(context)
            setOnClickListener {
                PreferenceDialog(
                    context,
                    PreferenceOptions(
                        this.label,
                        theme.toString(context),
                        themeOptions,
                        onOptionSelected = { option ->
                            viewModel.setTheme(
                                availableThemes[themeOptions.indexOf(option)]
                            )
                        }
                    )
                ).show()
            }
        }
    }

    private fun Theme.stringRes() = when (this.id) {
        R.style.Theme_GameboyDB_Day -> R.string.theme_day
        R.style.Theme_GameboyDB_Night -> R.string.theme_night
        R.style.Theme_GameboyDB_Orange -> R.string.theme_orange
        else -> R.string.theme_system
    }

    private fun Theme.toString(context: Context) = context.getString(stringRes())
}