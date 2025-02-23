package com.github.freitzzz.gameboydb.data.repository

import com.github.freitzzz.gameboydb.core.None
import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Option
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.core.Some
import com.github.freitzzz.gameboydb.data.model.Game

interface FavoritesRepository {
    suspend fun all(): OperationResult<List<Game>>
    suspend fun add(game: Game): OperationResult<Unit>
    suspend fun remove(game: Game): OperationResult<Unit>
    suspend fun find(id: String): OperationResult<Option<Game>>
}

class FakeFavoritesRepository : FavoritesRepository {
    private val favorites = mutableListOf<Game>()

    override suspend fun all(): OperationResult<List<Game>> {
        return Right(favorites)
    }

    override suspend fun add(game: Game): OperationResult<Unit> {
        favorites.add(game.copy(favorite = true))
        return Right(Unit)
    }

    override suspend fun remove(game: Game): OperationResult<Unit> {
        favorites.remove(game)
        return Right(Unit)
    }

    override suspend fun find(id: String): OperationResult<Option<Game>> {
        return Right(
            favorites.find { it.id == id }.let { if (it == null) None() else Some(it) }
        )
    }
}