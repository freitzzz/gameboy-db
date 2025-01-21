package com.github.freitzzz.gameboydb.view.fragment

import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.github.freitzzz.gameboydb.BuildConfig
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.custom.ValuesPreference
import com.github.freitzzz.gameboydb.view.openExternal
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewOf

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.view(R.id.settings_star_button).setOnClickListener {
            view.context.openExternal(
                Uri.parse(BuildConfig.REPOSITORY_URL)
            )
        }

        view.viewOf<ValuesPreference>(R.id.settings_preference_app_version).value =
            BuildConfig.VERSION_NAME
    }
}