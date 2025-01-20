package com.github.freitzzz.gameboydb.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Game(
    val id: String,
    val title: String,
    val description: String,
    val releaseYear: Int,
    val genres: List<String>,
    val developers: List<String>,
    val publishers: List<String>,
    val platforms: List<String>,
    val screenshots: List<Uri>,
    val esrb: ESRB = ESRB.RATING_PENDING,
    val favorite: Boolean = false,
    val promo: String? = null,
    val trivia: String? = null,
    val rating: Float? = null,
    val critics: Float? = null,
    val thumbnail: Uri? = null,
    val gameplay: Uri? = null
) : Parcelable

data class GamePreview(
    val id: String,
    val title: String,
    val genres: List<String>,
    val platforms: List<String>,
    val thumbnail: Uri? = null,
)

fun Game.preview() = GamePreview(
    id = this.id,
    title = this.title,
    genres = this.genres,
    thumbnail = this.thumbnail,
    platforms = this.platforms,
)