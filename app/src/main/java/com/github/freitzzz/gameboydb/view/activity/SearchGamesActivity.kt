package com.github.freitzzz.gameboydb.view.activity

import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.view.adapter.PaginatedRecyclerViewAdapter
import com.github.freitzzz.gameboydb.view.setText
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewOf
import com.github.freitzzz.gameboydb.view.viewmodel.SearchGamesViewModel

class SearchGamesActivity : AppActivity(R.layout.activity_search_games) {
    private val viewModel by lazy { viewModel<SearchGamesViewModel>() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewOf<SearchView>(R.id.activity_search_games_search_view).setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextChange(newText: String?): Boolean {
                    viewModel.onQuery(newText ?: "")
                    return true
                }

                override fun onQueryTextSubmit(query: String?): Boolean {
                    return true
                }
            }
        )

        val recyclerView = viewOf<RecyclerView>(
            R.id.activity_search_games_recycler_view
        )

        val adapter = PaginatedRecyclerViewAdapter<GamePreview>(
            itemLayoutId = R.layout.favorite_game_tile,
            onBind = { game ->
                setText(
                    R.id.favorite_game_name to game.name,
                    R.id.favorite_game_genre to game.genres.joinToString(", "),
                    R.id.favorite_game_platforms to game.platforms.joinToString("/ "),
                )

                // todo: rename favorite_game layout > game_preview layout
                viewOf<ImageView>(R.id.favorite_game_cover).setImageURI(game.thumbnail)
            },
            onLayoutParams = {
                bottomMargin = recyclerView.resources.getDimension(R.dimen.large_gap).toInt()
            },
            onNextPage = {
                viewModel.onScrollingToNextPage(viewOf<SearchView>(R.id.activity_search_games_search_view).query.toString())
            }
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.VERTICAL
        }

        viewModel.results.observe(this) {
            adapter.iterate(it)
        }
    }
}

