package com.github.freitzzz.gameboydb.data.repository

import androidx.core.net.toUri
import com.github.freitzzz.gameboydb.data.model.GameTile

interface TilesRepository {
    suspend fun top(): List<GameTile>
}

class FakeTilesRepository : TilesRepository {
    override suspend fun top(): List<GameTile> {
        return arrayListOf(
            GameTile(
                id = "game-id-1",
                title = "Pokemon Red",
                description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
                rating = 9.3f,
                cover = "https://static.wikia.nocookie.net/nintendo/images/5/59/Pokemon_Red_%28NA%29.png/revision/latest/scale-to-width-down/1000?cb=20240413173729&path-prefix=en".toUri(),
                screenshots = arrayListOf(),
                gameplay = "",
                genres = arrayListOf(
                    "Role-playing"
                ),
            ),
            GameTile(
                id = "game-id-2",
                title = "Tommy Jerry",
                description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
                rating = 9.3f,
                cover = "https://static.wikia.nocookie.net/nintendo/images/5/59/Pokemon_Red_%28NA%29.png/revision/latest/scale-to-width-down/1000?cb=20240413173729&path-prefix=en".toUri(),
                screenshots = arrayListOf(),
                gameplay = "",
                genres = arrayListOf(
                    "RPG"
                ),
            )
        )
    }
}