package com.github.freitzzz.gameboydb.di

import android.content.Context
import com.github.freitzzz.gameboydb.core.Vault
import com.github.freitzzz.gameboydb.data.http.SSLBypassNetworkingClient
import com.github.freitzzz.gameboydb.data.repository.AssetsRepository
import com.github.freitzzz.gameboydb.data.repository.FakeTilesRepository
import com.github.freitzzz.gameboydb.data.repository.NetworkingAssetsRepository
import com.github.freitzzz.gameboydb.data.repository.TilesRepository
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetControversialTiles
import com.github.freitzzz.gameboydb.domain.GetTopRatedTiles
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
    store<TilesRepository>(FakeTilesRepository())
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
    store(GetTopRatedTiles(get()))
    store(GetControversialTiles(get()))
    store(DownloadImage(get()))
}
