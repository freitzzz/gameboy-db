package com.github.freitzzz.gameboydb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView

open class RecyclerViewAdapter<T>(
    @LayoutRes private val itemLayoutId: Int,
    private val onBind: View.(data: T) -> Unit,
    private val onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
    private val data: MutableList<T> = arrayListOf(),
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

    fun addAll(data: List<T>) {
        val startIdx = this.data.size

        this.data.addAll(data)
        notifyItemRangeInserted(startIdx, data.size)
    }

    fun replaceAll(data: List<T>) {
        val beforeSize = this.data.size
        this.data.clear()
        notifyItemRangeRemoved(0, beforeSize)
        addAll(data)
    }

    fun set(data: List<T>) {
        val oldSize = this.data.size

        this.data.clear()
        this.data.addAll(data)

        this.notifyItemRangeRemoved(0, oldSize)
        if (data.isEmpty()) {
            return
        }

        this.notifyItemRangeInserted(0, this.data.size)
    }

    fun sync(data: List<T>) {
        val oldSize = this.data.size
        if (data.size < oldSize) {
            for (i in data.size..<oldSize) {
                this.data.removeAt(i)
            }

            notifyItemRangeRemoved(data.size, oldSize - data.size)
        }

        if (data.size > oldSize) {
            for (i in oldSize..<data.size) {
                this.data.add(data[i])
            }

            notifyItemRangeInserted(data.size, data.size - oldSize)
        }

        for (i in data.indices) {
            if (data[i] != this.data[i]) {
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