package com.github.freitzzz.gameboydb.view.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.view.activity.GameDetailsActivity
import com.github.freitzzz.gameboydb.view.navigateTo
import com.google.android.material.imageview.ShapeableImageView

class FavoriteGameAdapter(
    private val bottomMargin: Int,
) : RecyclerView.Adapter<FavoriteGameViewHolder>() {
    private val data = arrayListOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteGameViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.favorite_game_tile, parent, false)

        itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
            bottomMargin = this@FavoriteGameAdapter.bottomMargin
        }

        return FavoriteGameViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: FavoriteGameViewHolder, position: Int) {
        holder.bind(data[position])

        holder.itemView.setOnClickListener {
            it.context.navigateTo<GameDetailsActivity>(data[position])
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun postAll(tiles: List<Game>) {
        data.addAll(tiles)
        notifyItemChanged(data.size - 1)
    }
}

class FavoriteGameViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tile: Game?) {
        if (tile == null) {
            return
        }

        itemView.findViewById<TextView>(R.id.favorite_game_name).text = tile.title
        itemView.findViewById<TextView>(R.id.favorite_game_genre).text =
            tile.genres.joinToString(", ")
        itemView.findViewById<TextView>(R.id.favorite_game_platforms).text =
            tile.platforms.joinToString("/ ")
        itemView.findViewById<ShapeableImageView>(R.id.favorite_game_cover)
            .setImageURI(tile.cover)
    }
}