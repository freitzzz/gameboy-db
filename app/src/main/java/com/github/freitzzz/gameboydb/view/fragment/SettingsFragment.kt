package com.github.freitzzz.gameboydb.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.custom.ValuesPreference
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewOf
import com.github.freitzzz.gameboydb.view.viewmodel.SettingsViewModel

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = viewModel<SettingsViewModel>()
        view.view(R.id.settings_star_button).setOnClickListener {
            viewModel.openProjectRepository(view.context)
        }

        view.viewOf<ValuesPreference>(R.id.settings_preference_app_version).value =
            viewModel.appVersion()
    }
}