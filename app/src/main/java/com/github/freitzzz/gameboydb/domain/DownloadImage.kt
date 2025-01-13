package com.github.freitzzz.gameboydb.domain

import com.github.freitzzz.gameboydb.data.repository.AssetsRepository
import java.io.File

class DownloadImage(
    private val repository: AssetsRepository,
) {
    suspend operator fun invoke(url: String): File = repository.download(url)
}