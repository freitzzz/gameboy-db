package com.github.freitzzz.gameboydb.data.model

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class GameTile(
    val id: String,
    val title: String,
    val description: String,
    val rating: Float,
    val cover: Uri,
    val genres: List<String>,
    val screenshots: List<Uri>,
    val gameplay: String,
): Parcelable