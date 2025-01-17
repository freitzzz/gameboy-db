package com.github.freitzzz.gameboydb.di

import android.content.Context
import com.github.freitzzz.gameboydb.core.Vault
import com.github.freitzzz.gameboydb.data.http.SSLBypassNetworkingClient
import com.github.freitzzz.gameboydb.data.repository.AssetsRepository
import com.github.freitzzz.gameboydb.data.repository.FakeGamesRepository
import com.github.freitzzz.gameboydb.data.repository.NetworkingAssetsRepository
import com.github.freitzzz.gameboydb.data.repository.GamesRepository
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.FavoriteGame
import com.github.freitzzz.gameboydb.domain.GetControversialGames
import com.github.freitzzz.gameboydb.domain.GetTopRatedGames
import com.github.freitzzz.gameboydb.domain.UnfavoriteGame
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers

fun register(vault: Vault, context: Context) {
    registerData(vault, context)
    registerDomain(vault)
}

private fun registerData(
    vault: Vault,
    context: Context,
) = vault.apply {
    store<GamesRepository>(FakeGamesRepository())
    store<AssetsRepository>(
        NetworkingAssetsRepository(
            SSLBypassNetworkingClient(""),
            context,
            CoroutineScope(Dispatchers.IO)
        )
    )
}

private fun registerDomain(
    vault: Vault,
) = vault.apply {
    store(GetTopRatedGames(get()))
    store(GetControversialGames(get()))
    store(DownloadImage(get()))
    store(FavoriteGame(get()))
    store(UnfavoriteGame(get()))
}
