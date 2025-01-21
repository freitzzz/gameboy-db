package com.github.freitzzz.gameboydb.view.custom

import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.View
import android.widget.ImageButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.popBack
import com.github.freitzzz.gameboydb.view.remove
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewOf

class NavigableTopBar @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ConstraintLayout(context, attrs) {
    var title: String? = null
        set(value) {
            viewOf<TextView>(R.id.navigable_top_bar_title).let {
                it.text = value
                field = value
                if (value == null) it.remove()
            }
        }

    var subtitle: String? = null
        set(value) {
            viewOf<TextView>(R.id.navigable_top_bar_subtitle).let {
                it.text = value
                field = value
                if (value == null) it.remove()
            }
        }

    var primaryActionIcon: Int? = null
        set(value) {
            viewOf<ImageButton>(R.id.navigable_top_bar_primary_action).let {
                it.setImageResource(value ?: 0)
                field = value
            }
        }

    var secondaryActionIcon: Int? = null
        set(value) {
            viewOf<ImageButton>(R.id.navigable_top_bar_secondary_action).let {
                it.setImageResource(value ?: 0)
                field = value
            }
        }

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.NavigableTopBar)
        val title = typedArray.getString(R.styleable.NavigableTopBar_title)
        val subtitle = typedArray.getString(R.styleable.NavigableTopBar_subtitle)
        val primaryAction =
            typedArray.getResourceId(R.styleable.NavigableTopBar_primaryActionIcon, 0)
        val secondaryAction =
            typedArray.getResourceId(R.styleable.NavigableTopBar_secondaryActionIcon, 0)
        typedArray.recycle()

        View.inflate(context, R.layout.navigable_top_bar, this)
        this.title = title
        this.subtitle = subtitle
        this.primaryActionIcon = primaryAction
        this.secondaryActionIcon = secondaryAction

        view(R.id.navigable_top_bar_back_action).setOnClickListener {
            if (context is Activity) {
                context.popBack()
            }
        }
    }
}