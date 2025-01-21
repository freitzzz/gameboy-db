package com.github.freitzzz.gameboydb.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.SwitchCompat
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.setText
import com.github.freitzzz.gameboydb.view.viewOf

class SwitchPreference @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {
    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.SwitchPreference)
        val label = typedArray.getString(R.styleable.SwitchPreference_label)
        val value = typedArray.getBoolean(R.styleable.SwitchPreference_value, false)
        val icon = typedArray.getResourceId(R.styleable.SwitchPreference_icon, 0)
        typedArray.recycle()

        View.inflate(context, R.layout.layout_switch_preference, this)

        setText(R.id.switch_preference_label to (label ?: ""))
        viewOf<TextView>(R.id.switch_preference_label).setCompoundDrawablesRelativeWithIntrinsicBounds(
            icon, 0, 0, 0
        )
        viewOf<SwitchCompat>(R.id.switch_preference_value).isChecked = value
    }
}