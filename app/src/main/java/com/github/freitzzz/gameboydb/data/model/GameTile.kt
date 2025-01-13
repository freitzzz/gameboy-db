package com.github.freitzzz.gameboydb.data.model

data class GameTile(
    val id: String,
    val title: String,
    val description: String,
    val rating: Float,
    val cover: String,
    val genres: List<String>,
    val screenshots: List<String>,
    val gameplay: String,
)