package com.github.freitzzz.gameboydb.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.adapter.FavoriteGameAdapter
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewmodel.GamesViewModel

class FavoritesFragment: Fragment(R.layout.fragment_favorites) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = viewModel<GamesViewModel>()
        val recyclerView = view.findViewById<RecyclerView>(
            R.id.fragment_favorites_recycler_view
        )

        val adapter = FavoriteGameAdapter(
            recyclerView.resources.getDimension(R.dimen.normal_gap).toInt()
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.VERTICAL
        }

        val data = viewModel.favorites()
        data.observe(viewLifecycleOwner) {
            adapter.postAll(it)
        }
    }
}