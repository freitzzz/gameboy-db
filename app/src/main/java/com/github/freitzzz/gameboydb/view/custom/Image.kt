package com.github.freitzzz.gameboydb.view.custom

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import androidx.lifecycle.ViewModelStoreOwner
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewmodel.ImageLoaderViewModel
import com.google.android.material.imageview.ShapeableImageView

class Image @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : ShapeableImageView(context, attrs) {
    init {
        if (isInEditMode) {
            setImageResource(R.drawable.cover)
        }
    }

    override fun setImageURI(uri: Uri?) {
        if (uri == null) {
            return
        }

        if (uri.scheme == "file") {
            return super.setImageURI(uri)
        }

        val context = context
        if (context !is ViewModelStoreOwner) {
            return
        }

        context.viewModel<ImageLoaderViewModel>()(uri) {
            super.setImageURI(it)
        }
    }
}