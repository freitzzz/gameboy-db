package com.github.freitzzz.gameboydb.di

import android.content.Context
import com.github.freitzzz.gameboydb.core.Vault
import com.github.freitzzz.gameboydb.data.http.SSLBypassNetworkingClient
import com.github.freitzzz.gameboydb.data.repository.AssetsRepository
import com.github.freitzzz.gameboydb.data.repository.GamesRepository
import com.github.freitzzz.gameboydb.data.repository.NetworkingAssetsRepository
import com.github.freitzzz.gameboydb.data.repository.PreferencesRepository
import com.github.freitzzz.gameboydb.data.repository.DbApiGamesRepository
import com.github.freitzzz.gameboydb.data.repository.SharedPreferencesRepository
import com.github.freitzzz.gameboydb.domain.ApplyTheme
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.FavoriteGame
import com.github.freitzzz.gameboydb.domain.GetAvailableThemes
import com.github.freitzzz.gameboydb.domain.GetControversialGames
import com.github.freitzzz.gameboydb.domain.GetFavoriteGames
import com.github.freitzzz.gameboydb.domain.GetTheme
import com.github.freitzzz.gameboydb.domain.GetTopRatedGames
import com.github.freitzzz.gameboydb.domain.LoadGame
import com.github.freitzzz.gameboydb.domain.OpenLanguageSettings
import com.github.freitzzz.gameboydb.domain.SearchGames
import com.github.freitzzz.gameboydb.domain.UnfavoriteGame
import com.github.freitzzz.gameboydb.domain.state.GameUpdates
import com.github.freitzzz.gameboydb.domain.state.PreferenceUpdates
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
    store<GamesRepository>(DbApiGamesRepository(client = SSLBypassNetworkingClient("https://gameboydb.guackamollyapps.com")))
    store<AssetsRepository>(
        NetworkingAssetsRepository(
            SSLBypassNetworkingClient(""),
            context,
            CoroutineScope(Dispatchers.IO)
        )
    )
    store<PreferencesRepository>(
        SharedPreferencesRepository(
            context.getSharedPreferences("app", Context.MODE_PRIVATE)
        )
    )
}

private fun registerDomain(
    vault: Vault,
) = vault.apply {
    store(GameUpdates())
    store(PreferenceUpdates())

    store(GetAvailableThemes())
    store(GetTheme(get()))
    store(ApplyTheme(get(), get()))
    store(OpenLanguageSettings())

    store(DownloadImage(get()))
    store(GetTopRatedGames(get()))
    store(GetControversialGames(get()))
    store(GetFavoriteGames(get()))
    store(SearchGames(get()))
    store(LoadGame(get()))
    store(FavoriteGame(get(), get()))
    store(UnfavoriteGame(get(), get()))
}
