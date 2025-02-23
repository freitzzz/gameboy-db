package com.github.freitzzz.gameboydb.data.repository

import androidx.core.net.toUri
import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.core.map
import com.github.freitzzz.gameboydb.data.http.JsonResponse
import com.github.freitzzz.gameboydb.data.http.NetworkingClient
import com.github.freitzzz.gameboydb.data.http.mapJson
import com.github.freitzzz.gameboydb.data.model.ESRB
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.data.model.game
import com.github.freitzzz.gameboydb.data.model.gamePreview
import com.github.freitzzz.gameboydb.data.model.preview
import org.json.JSONObject

interface GamesRepository {
    suspend fun top(): OperationResult<List<GamePreview>>
    suspend fun controversial(): OperationResult<List<GamePreview>>
    suspend fun search(query: String, page: Int): OperationResult<List<GamePreview>>
    suspend fun find(id: String): OperationResult<Game>
}

class DbApiGamesRepository(
    private val client: NetworkingClient,
) : GamesRepository {
    override suspend fun top(): OperationResult<List<GamePreview>> {
        return client.get("/previews?rating=high").mapJson {
            it.jsonArray().map(JSONObject::gamePreview)
        }
    }

    override suspend fun controversial(): OperationResult<List<GamePreview>> {
        return client.get("/previews?rating=low").mapJson {
            it.jsonArray().map(JSONObject::gamePreview)
        }
    }

    override suspend fun search(query: String, page: Int): OperationResult<List<GamePreview>> {
        return client.get("/previews?name=$query&page=${page}").mapJson {
            it.jsonArray().map(JSONObject::gamePreview)
        }
    }

    override suspend fun find(id: String): OperationResult<Game> {
        return client.get("/details/$id").mapJson {
            it.json().game()
        }
    }
}

class FakeGamesRepository : GamesRepository {
    private val games = arrayListOf(
        Game(
            id = "game-id-1",
            name = "Pokemon Red",
            description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
            promo = """
                            Gotta catch 'em all!
                            You've finally been granted your Pokemon trainer's license. Now, it's time to head out to become the world's greatest Pokemon trainer. It's going to take all you've got to collect 150 Pokemon in this enormous world. Catch and train monsters like the shockingly-cute Pikachu. Face off against Blastoise's torrential water cannons. Stand strong when facing Pidgeot's stormy Gust. Trade with friends and watch your Pokemon evolve. Important - no single Pokemon can win it all. Can you develop the ultimate Pokemon strategy to defeat the eight GYM Leaders and become the great Pokemon Master of all time?
                            
                            * Collect up to 139 different Pokemon playing the Red version. Using the Game Link cable (sold separately), trade with a friend who has the Blue version to capture all 150.
                            * You'll need to use both versions (Red and Blue) to collect all of the Pokemon.
                            * Test your training skills by battling against a friend using the Game Link cable (sold separately).
                            * Save your Pokemon collection and game progress on the Game Pak memory.
                            * Requires basic reading skills to fully enjoy the entertaining story.
                    """.trimIndent(),
            trivia = """
                        Development
                        The man behind Pocket Monsters, Satoshi Tajiri, had been working on the game a full five years (from 1991 to 1996) before he finally decided to release it after he was satisfied enough with it. No one at Nintendo expected the game to be a hit, but it was.
                    """.trimIndent(),
            rating = 9.3f,
            critics = 90f,
            releaseYear = 1998,
            esrb = ESRB.EVERYONE,
            genres = arrayListOf(
                "Role-playing"
            ),
            developers = arrayListOf(
                "Nintendo Co., Ltd.",
                "Game Freak, Inc.",
                "Creatures, Inc.",
                "Contact Data Belgium N.V."
            ),
            publishers = arrayListOf(
                "Nintendo Co., Ltd.",
                "Nintendo of Europe GmbH",
            ),
            platforms = arrayListOf("Game Boy"),
            thumbnail = "https://cdn.mobygames.com/90e31a40-aba5-11ed-9e18-02420a00019a.webp".toUri(),
            screenshots = arrayListOf(
                "https://cdn.mobygames.com/ca55231c-bed9-11ed-9c42-02420a000140.webp",
                "https://cdn.mobygames.com/cb90d258-bed9-11ed-9c42-02420a000140.webp",
                "https://cdn.mobygames.com/cc07421c-bed9-11ed-9c42-02420a000140.webp",
                "https://cdn.mobygames.com/cc74855c-bed9-11ed-9c42-02420a000140.webp",
                "https://cdn.mobygames.com/be38a1a8-bed9-11ed-9c42-02420a000140.webp",
            ).map { it.toUri() },
        ),
        Game(
            id = "game-id-2",
            name = "Kirby's Dream Land 2",
            description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
            releaseYear = 1996,
            rating = 9.3f,
            thumbnail = "https://cdn.mobygames.com/covers/34622-kirbys-dream-land-2-game-boy-front-cover.jpg".toUri(),
            screenshots = arrayListOf(),
            developers = arrayListOf(),
            publishers = arrayListOf(),
            platforms = arrayListOf("Game Boy"),
            genres = arrayListOf(
                "Platformer",
                "RPG",
                "Action",
                "Fantasy",
                "Nintendo"
            ),
        ),
        Game(
            id = "game-id-3",
            name = "Donkey Kong Land",
            description = "Pokémon is a role-playing game. In a departure from traditional RPGs, however, the player's Pokémon fight instead of the player himself with one of the main goals to collect all the available monsters.",
            releaseYear = 1996,
            rating = 9.3f,
            thumbnail = "https://cdn.mobygames.com/8b6c7a7c-aba8-11ed-b9a3-02420a000197.webp".toUri(),
            screenshots = arrayListOf(),
            developers = arrayListOf(),
            publishers = arrayListOf(),
            platforms = arrayListOf("Game Boy"),
            genres = arrayListOf(
                "Platformer"
            ),
        ),
        Game(
            id = "game-id-4",
            name = "Men in Black: The Series",
            description = "Evil alien races are infiltrating New York, and it's up to the Men in Black to stop them. In the game the player assume the role of rookie agent J as he single-handedly takes on the invasion in order to stop the mastermind Alpha (a mutated ex-MIB) and save Earth.",
            releaseYear = 1996,
            rating = 4.4f,
            thumbnail = "https://cdn.mobygames.com/4ffb72a4-ab7b-11ed-bddc-02420a00019e.webp".toUri(),
            screenshots = arrayListOf(),
            developers = arrayListOf(),
            publishers = arrayListOf(),
            platforms = arrayListOf("Game Boy"),
            genres = arrayListOf(
                "Action"
            ),
        ),
        Game(
            id = "game-id-5",
            name = "Shrek: Fairy Tale Freakdown",
            description = "In this 2D one-on-one fighting game, you can choose to play as characters from the movie Shrek, including Shrek himself, the Gingerbread Man, Thelonius, and six other included personalities. All characters have their own signature moves, but as you move on through the quest more special powers are unlocked - these include speed, invincibility, and strength.",
            releaseYear = 1996,
            rating = 4.5f,
            thumbnail = "https://cdn.mobygames.com/0e98b27c-aba3-11ed-98cd-02420a00019e.webp".toUri(),
            screenshots = arrayListOf(),
            developers = arrayListOf(),
            publishers = arrayListOf(),
            platforms = arrayListOf("Game Boy"),
            genres = arrayListOf(
                "Action"
            ),
        ),
        Game(
            id = "game-id-6",
            name = "Soccer Mania",
            description = "Soccer Mania is a soccer game with cartoon graphics. The game features six teams (Japan, USA, Brazil, England, Germany and France) and the player chooses one of them. The goal is to win a championship by defeating all other five teams, followed by a final match against the All-Star Team. There is only a part of the playfield shown at a time and the rest of the playfield scrolls - the goals are positioned vertically.",
            releaseYear = 1996,
            rating = 4.5f,
            thumbnail = "https://cdn.mobygames.com/59192f8a-c222-11ed-ab6b-02420a000194.webp".toUri(),
            screenshots = arrayListOf(),
            developers = arrayListOf(),
            publishers = arrayListOf(),
            platforms = arrayListOf("Game Boy"),
            genres = arrayListOf(
                "Platformer"
            ),
        )
    )

    private val games2 = (1..5).flatMap { games }

    override suspend fun top(): OperationResult<List<GamePreview>> {
        return Right(games.take(3).map(Game::preview))
    }

    override suspend fun controversial(): OperationResult<List<GamePreview>> {
        return Right(
            games.reversed().take(3).map(Game::preview)
        )
    }

    override suspend fun search(query: String, page: Int): OperationResult<List<GamePreview>> {
        return Right(
            games2
                .drop((page - 1) * 5)
                .take(5).filter { it.name.lowercase().contains(query) }
                .map(Game::preview)
        )
    }

    override suspend fun find(id: String): OperationResult<Game> {
        val game = games.first { it.id == id }
        return Right(game)
    }
}