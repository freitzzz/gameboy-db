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
                cover = "https://cdn.mobygames.com/90e31a40-aba5-11ed-9e18-02420a00019a.webp".toUri(),
                screenshots = arrayListOf(),
                gameplay = "",
                genres = arrayListOf(
                    "Role-playing"
                ),
            ),
            GameTile(
                id = "game-id-2",
                title = "Kirby's Dream Land 2",
                description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
                rating = 9.3f,
                cover = "https://cdn.mobygames.com/covers/34622-kirbys-dream-land-2-game-boy-front-cover.jpg".toUri(),
                screenshots = arrayListOf(),
                gameplay = "",
                genres = arrayListOf(
                    "Platformer",
                    "RPG",
                    "Action",
                    "Fantasy",
                    "Nintendo"
                ),
            ),
            GameTile(
                id = "game-id-3",
                title = "Donkey Kong Land",
                description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
                rating = 9.3f,
                cover = "https://cdn.mobygames.com/8b6c7a7c-aba8-11ed-b9a3-02420a000197.webp".toUri(),
                screenshots = arrayListOf(),
                gameplay = "",
                genres = arrayListOf(
                    "Platformer"
                ),
            )
        )
    }
}