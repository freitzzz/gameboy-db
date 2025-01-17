package com.github.freitzzz.gameboydb.view.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.activity.GameDetailsActivity
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.view.navigateTo
import com.github.freitzzz.gameboydb.view.viewmodel.GameTileViewModel
import com.google.android.material.imageview.ShapeableImageView

class GalleryGameSlideShow : Fragment(R.layout.fragment_gallery_game_slide_show) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[GameTileViewModel::class.java]
        val recyclerView = view.findViewById<RecyclerView>(
            R.id.fragment_gallery_game_slide_show_recycler_view
        )

        val adapter = GameTilesAdapter(
            recyclerView.resources.getDimension(R.dimen.large_gap).toInt()
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.HORIZONTAL
        }

        val parent = view.parent
        if (parent !is FragmentContainerView) {
            return
        }

        val data = when (parent.id) {
            R.id.fragment_gallery_top_rated_game_slide_show -> viewModel.topRated()
            R.id.fragment_gallery_controversial_game_slide_show -> viewModel.controversial()
            else -> null
        }

        data?.observe(viewLifecycleOwner) {
            adapter.postAll(it)
        }
    }
}

class GameTilesAdapter(
    private val endMargin: Int,
) : RecyclerView.Adapter<GameTileViewHolder>() {
    private val data = arrayListOf<Game>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameTileViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.gallery_game_tile, parent, false)

        itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
            marginEnd = endMargin
        }

        return GameTileViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameTileViewHolder, position: Int) {
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

class GameTileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(tile: Game?) {
        if (tile == null) {
            return
        }

        itemView.findViewById<TextView>(R.id.gallery_game_tile_name).text = tile.title
        itemView.findViewById<TextView>(R.id.gallery_game_tile_genre).text =
            tile.genres.joinToString(", ")
        itemView.findViewById<ShapeableImageView>(R.id.gallery_game_tile_cover)
            .setImageURI(tile.cover)
    }
}