package com.github.freitzzz.gameboydb.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * Complete schema of game metadata.
 */
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
    val cover: Uri? = null,
    val gameplay: Uri? = null
) : Parcelable