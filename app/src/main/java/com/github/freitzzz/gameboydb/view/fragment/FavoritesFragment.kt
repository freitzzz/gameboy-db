package com.github.freitzzz.gameboydb.view.fragment

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.view.activity.GameDetailsActivity
import com.github.freitzzz.gameboydb.view.adapter.RecyclerViewAdapter
import com.github.freitzzz.gameboydb.view.navigateTo
import com.github.freitzzz.gameboydb.view.setText
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewOf
import com.github.freitzzz.gameboydb.view.viewmodel.GamesViewModel
import com.github.freitzzz.gameboydb.view.viewmodel.ValueChangeEvent

class FavoritesFragment : Fragment(R.layout.fragment_favorites) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = viewModel<GamesViewModel>()
        val recyclerView = view.findViewById<RecyclerView>(
            R.id.fragment_favorites_recycler_view
        )

        val adapter = RecyclerViewAdapter<GamePreview>(
            itemLayoutId = R.layout.favorite_game_tile,
            onBind = { game ->
                setText(
                    R.id.favorite_game_name to game.title,
                    R.id.favorite_game_genre to game.genres.joinToString(", "),
                    R.id.favorite_game_platforms to game.platforms.joinToString("/ "),
                )

                viewOf<ImageView>(R.id.favorite_game_cover).setImageURI(game.thumbnail)
                setOnClickListener {
                    viewModel.load(game) {
                        context.navigateTo<GameDetailsActivity>(it)
                    }
                }
            },
            onLayoutParams = {
                bottomMargin = recyclerView.resources.getDimension(R.dimen.large_gap).toInt()
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.VERTICAL
        }

        val data = viewModel.favorites()
        data.observe(viewLifecycleOwner) {
            adapter.sync(it)
        }
    }
}