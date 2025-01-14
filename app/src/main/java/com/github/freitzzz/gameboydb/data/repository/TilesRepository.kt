package com.github.freitzzz.gameboydb.data.repository

import androidx.core.net.toUri
import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.data.model.GameTile

interface TilesRepository {
    suspend fun top(): OperationResult<List<GameTile>>
    suspend fun controversial(): OperationResult<List<GameTile>>
}

class FakeTilesRepository : TilesRepository {
    override suspend fun top(): OperationResult<List<GameTile>> {
        return Right(
            arrayListOf(
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
        )
    }

    override suspend fun controversial(): OperationResult<List<GameTile>> {
        return Right(
            arrayListOf(
                GameTile(
                    id = "game-id-4",
                    title = "Men in Black: The Series",
                    description = "Evil alien races are infiltrating New York, and it's up to the Men in Black to stop them. In the game the player assume the role of rookie agent J as he single-handedly takes on the invasion in order to stop the mastermind Alpha (a mutated ex-MIB) and save Earth.",
                    rating = 4.4f,
                    cover = "https://cdn.mobygames.com/4ffb72a4-ab7b-11ed-bddc-02420a00019e.webp".toUri(),
                    screenshots = arrayListOf(),
                    gameplay = "",
                    genres = arrayListOf(
                        "Action"
                    ),
                ),
                GameTile(
                    id = "game-id-5",
                    title = "Shrek: Fairy Tale Freakdown",
                    description = "In this 2D one-on-one fighting game, you can choose to play as characters from the movie Shrek, including Shrek himself, the Gingerbread Man, Thelonius, and six other included personalities. All characters have their own signature moves, but as you move on through the quest more special powers are unlocked - these include speed, invincibility, and strength.",
                    rating = 4.5f,
                    cover = "https://cdn.mobygames.com/0e98b27c-aba3-11ed-98cd-02420a00019e.webp".toUri(),
                    screenshots = arrayListOf(),
                    gameplay = "",
                    genres = arrayListOf(
                        "Action"
                    ),
                ),
                GameTile(
                    id = "game-id-6",
                    title = "Soccer Mania",
                    description = "Soccer Mania is a soccer game with cartoon graphics. The game features six teams (Japan, USA, Brazil, England, Germany and France) and the player chooses one of them. The goal is to win a championship by defeating all other five teams, followed by a final match against the All-Star Team. There is only a part of the playfield shown at a time and the rest of the playfield scrolls - the goals are positioned vertically.",
                    rating = 4.5f,
                    cover = "https://cdn.mobygames.com/59192f8a-c222-11ed-ab6b-02420a000194.webp".toUri(),
                    screenshots = arrayListOf(),
                    gameplay = "",
                    genres = arrayListOf(
                        "Platformer"
                    ),
                )
            )
        )
    }
}