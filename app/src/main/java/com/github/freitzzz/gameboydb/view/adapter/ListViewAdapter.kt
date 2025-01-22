package com.github.freitzzz.gameboydb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import androidx.annotation.LayoutRes

class ListViewAdapter<T>(
    @LayoutRes private val itemLayoutId: Int,
    private val onBind: View.(data: T) -> Unit,
    private val onLayoutParams: LinearLayout.LayoutParams.() -> Unit = {},
    private val data: MutableList<T> = arrayListOf()
) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        if (convertView == null) {
            val itemView: View =
                LayoutInflater.from(parent!!.context).inflate(itemLayoutId, parent, false)

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