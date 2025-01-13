package com.github.freitzzz.gameboydb.data.repository

import android.content.Context
import android.net.Uri
import com.github.freitzzz.gameboydb.core.DownloadError
import com.github.freitzzz.gameboydb.core.Left
import com.github.freitzzz.gameboydb.core.OperationResult
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.data.http.NetworkingClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File

interface AssetsRepository {
    suspend fun download(url: String): OperationResult<File>
}

class NetworkingAssetsRepository(
    private val client: NetworkingClient,
    private val context: Context,
    private val scope: CoroutineScope,
) : AssetsRepository {
    override suspend fun download(url: String): OperationResult<File> {
        return runBlocking(scope.coroutineContext) {
            val filename = Uri.parse(url)?.path?.substringAfterLast('/')
                ?: return@runBlocking Left(DownloadError("not a valid url (=> $url)"))

            val dir = context.cacheDir
            val files = dir.listFiles { it -> it.path.endsWith(filename) }

            if (files != null && files.isNotEmpty()) {
                return@runBlocking Right(files[0])
            }

            val result = client.download(url)
            result.map {
                val file = File(dir, filename)
                file.writeBytes(it.body)
                return@map file
            }
        }
    }
}