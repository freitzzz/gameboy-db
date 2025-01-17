package com.github.freitzzz.gameboydb.view.adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

/**
 * A [RecyclerView.Adapter] for [ImageView] layouts.
 */
class ImagesAdapter(
    @LayoutRes private val layoutId: Int,
    private val endMargin: Int,
    private val data: MutableList<Uri> = arrayListOf()
) : RecyclerView.Adapter<ImageViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(layoutId, parent, false)

        itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
            marginEnd = endMargin
        }

        return ImageViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun postAll(screenshots: List<Uri>) {
        data.addAll(screenshots)
        notifyItemChanged(data.size - 1)
    }

    fun updateAll(images: List<Uri>) {
        for (i in images.indices) {
            if (data[i] != images[i]) {
                data[i] = images[i]
                notifyItemChanged(i)
            }
        }
    }
}

class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(image: Uri?) {
        if (image == null) {
            return
        }

        if (image.scheme == "file") {
            (itemView as ImageView).setImageURI(image)
        }
    }
}