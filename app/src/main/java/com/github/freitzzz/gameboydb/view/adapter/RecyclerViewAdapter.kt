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

    fun remove(data: List<T>) {
        for (i in data.indices) {
            for (j in this.data.indices) {
                if (this.data[j] == data[i]) {
                    this.data.removeAt(j)
                    notifyItemRemoved(j)

                    break
                }
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