package com.github.freitzzz.gameboydb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

class ListViewAdapter<T>(
    private val onCreateView: ViewGroup.(Int) -> View,
    private val onBind: View.(data: T) -> Unit,
    private val onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
    private val data: MutableList<T> = arrayListOf()
) : BaseAdapter() {
    constructor(
        @LayoutRes itemLayoutId: Int,
        onBind: View.(data: T) -> Unit,
        onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
        data: MutableList<T> = arrayListOf()
    ) : this(
        onCreateView = { LayoutInflater.from(this.context).inflate(itemLayoutId, this, false) },
        onBind = onBind,
        onLayoutParams = onLayoutParams,
        data = data
    )

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val itemView = onCreateView(parent!!, position)
            itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
                onLayoutParams()
            }

            itemView.onBind(getItem(position))

            return itemView
        }

        return convertView
    }

    override fun getCount() = data.size
    override fun getItem(position: Int) = data[position]
    override fun getItemId(position: Int) = position.toLong()
}