package com.github.freitzzz.gameboydb.view.activity

import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.core.allOf
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.view.displayMetrics
import com.github.freitzzz.gameboydb.view.drawable
import com.github.freitzzz.gameboydb.view.drawableRes
import com.github.freitzzz.gameboydb.view.get
import com.github.freitzzz.gameboydb.view.landscape
import com.github.freitzzz.gameboydb.view.setText
import com.github.freitzzz.gameboydb.view.show
import com.github.freitzzz.gameboydb.view.view
import com.github.freitzzz.gameboydb.view.viewModel
import com.github.freitzzz.gameboydb.view.viewOf
import com.github.freitzzz.gameboydb.view.withRes
import com.github.freitzzz.gameboydb.view.viewmodel.ImageLoaderViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator


class GameDetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val game = intent.get<Game>()!!

        setText(
            R.id.game_details_sheet_title to game.title,
            R.id.game_details_sheet_description to game.description,
            R.id.game_details_sheet_promo to game.promo,
            R.id.game_details_sheet_trivia to game.trivia,
            R.id.game_details_sheet_genres to game.genres.joinToString(", "),
            R.id.game_details_sheet_platforms to game.platforms.joinToString("/ "),
            R.id.game_details_sheet_developers to game.developers.joinToString("\n"),
            R.id.game_details_sheet_publishers to game.publishers.joinToString("\n"),
            R.id.game_details_sheet_release_year to game.releaseYear.toString(),
            R.id.game_rating_text to game.rating.toString(),
        )

        withRes(
            allOf(
                R.id.game_details_sheet_promo_heading to (game.promo != null),
                R.id.game_details_sheet_promo to (game.promo != null),
                R.id.game_details_sheet_trivia_heading to (game.trivia != null),
                R.id.game_details_sheet_trivia to (game.trivia != null),
                R.id.game_details_sheet_publishers to game.publishers.isNotEmpty(),
                R.id.game_details_sheet_publishers_heading to game.publishers.isNotEmpty(),
                R.id.game_details_sheet_developers_heading to game.developers.isNotEmpty(),
                R.id.game_details_sheet_developers to game.developers.isNotEmpty(),
            )
        ).show()

        viewOf<ImageView>(R.id.game_details_sheet_esrb_rating).setImageDrawable(drawable(game.esrb.drawableRes()))
        viewOf<ImageView>(R.id.game_details_cover).setImageURI(game.cover)
        viewOf<CircularProgressIndicator>(R.id.game_rating_progress_indicator).progress =
            game.rating?.times(10)?.toInt() ?: 0

        val sheet = BottomSheetBehavior.from(view(R.id.game_details_sheet))
        sheet.maxHeight = (displayMetrics().heightPixels * if (landscape()) 0.8 else 0.9).toInt()

        val recyclerView = viewOf<RecyclerView>(
            R.id.game_details_sheet_screenshots
        )

        val adapter = GameScreenshotsAdapter(
            recyclerView.resources.getDimension(R.dimen.large_gap).toInt()
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.HORIZONTAL
        }

        adapter.postAll(game.screenshots)
        val viewModel = this.viewModel<ImageLoaderViewModel>()
        if (!viewModel.loaded(game.screenshots)) {
            viewModel.invoke(game.screenshots).observe(this) {
                adapter.updateAll(it)
            }
        }
    }
}

class GameScreenshotsAdapter(
    private val endMargin: Int,
) : RecyclerView.Adapter<GameScreenshotViewHolder>() {
    private val data = arrayListOf<Uri>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameScreenshotViewHolder {
        val itemView: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.game_details_screenshot, parent, false)

        itemView.layoutParams = LinearLayout.LayoutParams(itemView.layoutParams).apply {
            marginEnd = endMargin
        }

        return GameScreenshotViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GameScreenshotViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int {
        return data.size
    }

    fun postAll(screenshots: List<Uri>) {
        data.addAll(screenshots)
        notifyItemChanged(data.size - 1)
    }

    fun updateAll(screenshots: List<Uri>) {
        for (i in screenshots.indices) {
            if (data[i] != screenshots[i]) {
                data[i] = screenshots[i]
                notifyItemChanged(i)
            }
        }
    }
}

class GameScreenshotViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    fun bind(screenshot: Uri?) {
        if (screenshot == null) {
            return
        }

        if (screenshot.scheme == "file") {
            (itemView as ShapeableImageView).setImageURI(screenshot)
        }
    }
}