package com.github.freitzzz.gameboydb.view.custom

import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.remove
import com.github.freitzzz.gameboydb.view.show
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewOf

class PreferenceListItem @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var value: String? = null
        set(value) {
            viewOf<TextView>(R.id.preference_list_item_value).let {
                it.text = value
                field = value
            }
        }

    var checked: Boolean = false
        set(value) {
            view(R.id.preference_list_item_check).let {
                if (value) it.show() else it.remove()
                field = value
            }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.PreferenceListItem)
        val value = typedArray.getString(R.styleable.PreferenceListItem_value)
        val checked = typedArray.getBoolean(R.styleable.PreferenceListItem_checked, false)
        typedArray.recycle()

        View.inflate(context, R.layout.layout_preference_list_item, this)
        this.value = value
        this.checked = checked

        if(isInEditMode){
            this.value = "Value"
            this.checked = true
        }
    }
}