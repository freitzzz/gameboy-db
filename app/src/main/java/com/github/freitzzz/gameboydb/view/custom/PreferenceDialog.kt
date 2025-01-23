package com.github.freitzzz.gameboydb.view.custom

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.widget.ListView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.adapter.ListViewAdapter
import com.github.freitzzz.gameboydb.view.custom.model.PreferenceOptions

class PreferenceDialog(
    context: Context,
    private val preference: PreferenceOptions
) : Dialog(context, R.style.screenshot_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_preference_dialog)

        val items = arrayListOf<CheckedListItem>()

        findViewById<NavigableTopBar>(R.id.navigable_top_bar).title = preference.label
        findViewById<ListView>(R.id.preference_values_list).apply {
            adapter = ListViewAdapter(
                onCreateView = { CheckedListItem(this.context) },
                onBind = {
                    if (this !is CheckedListItem) return@ListViewAdapter
                    items.add(this)

                    value = it
                    checked = it == preference.value

                    setOnClickListener { _ ->
                        items.first { item -> item.checked }.checked = false
                        checked = true

                        preference.onOptionSelected(it)
                    }
                },
                data = preference.options.toMutableList()
            )
        }
    }
}