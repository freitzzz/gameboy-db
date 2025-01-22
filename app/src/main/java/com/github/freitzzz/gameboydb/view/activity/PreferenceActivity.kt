package com.github.freitzzz.gameboydb.view.activity

import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.adapter.ListViewAdapter
import com.github.freitzzz.gameboydb.view.custom.NavigableTopBar
import com.github.freitzzz.gameboydb.view.custom.model.Preference
import com.github.freitzzz.gameboydb.view.get
import com.github.freitzzz.gameboydb.view.remove
import com.github.freitzzz.gameboydb.view.setText
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewOf

class PreferenceActivity : AppCompatActivity(R.layout.activity_prefence) {
    private val preference by lazy { intent.get<Preference<*>>()!! }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewOf<NavigableTopBar>(R.id.navigable_top_bar).title = preference.label
        viewOf<ListView>(R.id.preference_values_list).apply {
            adapter = ListViewAdapter(
                itemLayoutId = R.layout.layout_preference_list_item,
                onBind = {
                    setText(R.id.preference_list_item_value to it.toString())
                    if (it != preference.value) {
                        view(R.id.preference_list_item_check).remove()
                    }
                },
                data = preference.options.toMutableList()
            )
        }
    }
}