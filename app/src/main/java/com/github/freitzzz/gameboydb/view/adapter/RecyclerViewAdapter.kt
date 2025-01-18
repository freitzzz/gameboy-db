package com.github.freitzzz.gameboydb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewAdapter<T>(
    @LayoutRes private val itemLayoutId: Int,
    private val onBind: View.(data: T) -> Unit,
    private val onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
    private val data: MutableList<T> = arrayListOf()
) : RecyclerView.Adapter<ItemViewHolder<T>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<T> {
        val itemView: View =
            LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)

        itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
            onLayoutParams()
        }

        return ItemViewHolder(itemView, onBind)
    }

    override fun onBindViewHolder(holder: ItemViewHolder<T>, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun postAll(data: List<T>) {
        this.data.addAll(data)
        notifyItemChanged(data.size - 1)
    }

    fun updateAll(data: List<T>) {
        for (i in data.indices) {
            if (this.data[i] != data[i]) {
                this.data[i] = data[i]
                notifyItemChanged(i)
            }
        }
    }
}

class ItemViewHolder<T>(
    itemView: View,
    private val onBind: View.(data: T) -> Unit,
) : RecyclerView.ViewHolder(itemView) {
    fun bind(data: T?) {
        if (data == null) return else itemView.onBind(data)
    }
}