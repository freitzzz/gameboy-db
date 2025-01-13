package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.AssetsRepository

class DownloadImage(
    private val repository: AssetsRepository,
) {
    suspend operator fun invoke(url: String) = repository.download(url)
}