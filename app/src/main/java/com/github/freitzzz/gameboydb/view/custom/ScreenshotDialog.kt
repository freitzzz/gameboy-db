package com.github.freitzzz.gameboydb.view.custom

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.ActionBar.LayoutParams
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.displayMetrics

class ScreenshotDialog(context: Context, private val uri: Uri) :
    Dialog(context, R.style.screenshot_dialog) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(
            ImageView(context).apply {
                setImageURI(uri)
                scaleType = ImageView.ScaleType.FIT_XY
                contentDescription = context.resources.getText(
                    R.string.game_screenshot_content_description
                )
                layoutParams = LinearLayout.LayoutParams(
                    LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT
                )
            }
        )
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        context.displayMetrics().apply {
            window!!.setLayout((widthPixels * 0.8).toInt(), (heightPixels * 0.5).toInt())
        }
    }
}