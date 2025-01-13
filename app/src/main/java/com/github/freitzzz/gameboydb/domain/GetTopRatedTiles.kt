package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.TilesRepository
import com.github.freitzzz.gameboydb.data.model.GameTile

class GetTopRatedTiles(
    private val tilesRepository: TilesRepository,
) {
    suspend operator fun invoke(): List<GameTile> = tilesRepository.top()
}