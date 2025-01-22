package com.github.freitzzz.gameboydb.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.viewOf

class ValuesPreference @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    var label = ""
        set(value) = viewOf<TextView>(R.id.values_preference_label).let {
            it.text = value
            field = value
        }

    var value = ""
        set(value) = viewOf<TextView>(R.id.values_preference_value).let {
            it.text = value
            field = value
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.ValuesPreference)
        val label = typedArray.getString(R.styleable.ValuesPreference_label)
        val value = typedArray.getString(R.styleable.ValuesPreference_value)
        val icon = typedArray.getResourceId(R.styleable.ValuesPreference_icon, 0)
        val editable = typedArray.getBoolean(R.styleable.ValuesPreference_editable, true)
        typedArray.recycle()

        View.inflate(context, R.layout.layout_values_preference, this)
        label?.let { this.label = it }
        value?.let { this.value = it }

        viewOf<TextView>(R.id.values_preference_label).setCompoundDrawablesRelativeWithIntrinsicBounds(
            icon, 0, 0, 0
        )

        if (!editable) {
            viewOf<TextView>(R.id.values_preference_value).setCompoundDrawablesRelativeWithIntrinsicBounds(
                0, 0, 0, 0
            )
        }
    }
}