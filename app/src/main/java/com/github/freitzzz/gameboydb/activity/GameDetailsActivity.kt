package com.github.freitzzz.gameboydb.activity

import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.ESRB
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.navigation.get
import com.github.freitzzz.gameboydb.viewmodel.ImageLoaderViewModel
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.imageview.ShapeableImageView
import com.google.android.material.progressindicator.CircularProgressIndicator


class GameDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game_details)

        val tile = intent.get<Game>()!!
        val sheet = BottomSheetBehavior.from(findViewById(R.id.game_details_sheet))
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        this.windowManager.defaultDisplay.rotation

        sheet.maxHeight =
            (displayMetrics.heightPixels * if (resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 0.9 else 0.8).toInt()
        val location = IntArray(2)

        /*findViewById<View>(R.id.navigable_top_bar_title).getLocationOnScreen(location)
        println(location[0])
        println(location[1])*/

        findViewById<TextView>(R.id.game_details_sheet_release_year).text =
            tile.releaseYear.toString()
        tile.promo?.let {
            findViewById<View>(R.id.game_details_sheet_promo_heading).visibility = View.VISIBLE
            findViewById<TextView>(R.id.game_details_sheet_promo).visibility = View.VISIBLE
            findViewById<TextView>(R.id.game_details_sheet_promo).text = it
        }

        tile.trivia?.let {
            findViewById<View>(R.id.game_details_sheet_trivia_heading).visibility =
                View.VISIBLE
            findViewById<TextView>(R.id.game_details_sheet_trivia).visibility = View.VISIBLE
            findViewById<TextView>(R.id.game_details_sheet_trivia).text = it
        }

        if (tile.developers.isNotEmpty()) {
            findViewById<TextView>(R.id.game_details_sheet_developers).apply {
                visibility = View.VISIBLE
                text = tile.developers.joinToString("\n")
            }
        }

        if (tile.publishers.isNotEmpty()) {
            findViewById<View>(R.id.game_details_sheet_developers_heading).visibility =
                View.VISIBLE
            findViewById<TextView>(R.id.game_details_sheet_developers).apply {
                visibility = View.VISIBLE
                text = tile.publishers.joinToString("\n")
            }
        }

        findViewById<ImageView>(R.id.game_details_sheet_esrb_rating).setImageDrawable(
            resources.getDrawable(tile.esrb.drawable())
        )


        findViewById<TextView>(R.id.game_details_sheet_title).apply {
            text = tile.title
        }

        findViewById<TextView>(R.id.game_details_sheet_genres).apply {
            text = tile.genres.joinToString(", ")
        }

        findViewById<TextView>(R.id.game_details_sheet_platforms).apply {
            text = tile.platforms.joinToString("/ ")
        }

        findViewById<View>(R.id.navigable_top_bar_subtitle).apply {
            this.visibility = View.GONE
        }

        findViewById<View>(R.id.navigable_top_bar_secondary_action).apply {
            this.visibility = View.GONE
        }

        findViewById<ImageView>(R.id.game_details_cover).apply {
            setImageURI(tile?.cover)
        }

        findViewById<TextView>(R.id.game_rating_text).apply {
            text = tile?.rating.toString()
        }

        findViewById<TextView>(R.id.game_details_sheet_description).apply {
            text = tile?.description
        }

        findViewById<CircularProgressIndicator>(R.id.game_rating_progress_indicator).apply {
            progress = tile?.rating?.times(10)?.toInt() ?: 0
        }

        val recyclerView = findViewById<RecyclerView>(
            R.id.game_details_sheet_screenshots
        )

        val adapter = GameScreenshotsAdapter(
            recyclerView.resources.getDimension(R.dimen.large_gap).toInt()
        )

        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context).apply {
            orientation = RecyclerView.HORIZONTAL
        }

        adapter.postAll(tile.screenshots)
        val viewModel = ViewModelProvider(this)[ImageLoaderViewModel::class.java]
        if (!viewModel.loaded(tile.screenshots)) {
            viewModel.invoke(tile.screenshots).observe(this) {
                println(it)
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

        println(screenshot.scheme)
        if (screenshot.scheme == "file") {
            (itemView as ShapeableImageView).setImageURI(screenshot)
        }
    }
}

fun ESRB.drawable() = when (this) {
    ESRB.RATING_PENDING -> R.drawable.esrb_rating_pending
    ESRB.EARLY_CHILDHOOD -> R.drawable.esrb_early_childhood
    ESRB.EVERYONE -> R.drawable.esrb_everyone
    ESRB.EVERYONE_ABOVE_10 -> R.drawable.esrb_everyone_above_10
    ESRB.TEEN -> R.drawable.esrb_teen
    ESRB.MATURE -> R.drawable.esrb_mature
    ESRB.ADULT -> R.drawable.esrb_adult
}