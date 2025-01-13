package com.github.freitzzz.gameboydb.di

import com.github.freitzzz.gameboydb.core.Vault
import com.github.freitzzz.gameboydb.data.FakeTilesRepository
import com.github.freitzzz.gameboydb.data.TilesRepository
import com.github.freitzzz.gameboydb.domain.GetTopRatedTiles

fun register(vault: Vault) {
    registerData(vault)
    registerDomain(vault)
}

private fun registerData(
    vault: Vault,
) = vault.apply {
    store<TilesRepository>(FakeTilesRepository())
}

private fun registerDomain(
    vault: Vault,
) = vault.apply {
    store(GetTopRatedTiles(get()))
}
