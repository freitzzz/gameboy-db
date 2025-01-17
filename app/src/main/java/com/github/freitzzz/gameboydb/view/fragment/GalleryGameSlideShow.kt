package com.github.freitzzz.gameboydb.view.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.view.adapter.GameTilesAdapter
import com.github.freitzzz.gameboydb.view.viewmodel.GamesViewModel

class GalleryGameSlideShow : Fragment(R.layout.fragment_gallery_game_slide_show) {
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val viewModel = ViewModelProvider(requireActivity())[GamesViewModel::class.java]
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

