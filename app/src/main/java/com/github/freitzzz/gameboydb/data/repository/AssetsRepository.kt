package com.github.freitzzz.gameboydb.data.repository

import android.content.Context
import com.github.freitzzz.gameboydb.data.http.NetworkingClient
import com.github.freitzzz.gameboydb.data.http.Right
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import java.io.File
import java.util.UUID

interface AssetsRepository {
    suspend fun download(url: String): File
}

class NetworkingAssetsRepository(
    private val client: NetworkingClient,
    private val context: Context,
    private val scope: CoroutineScope,
) : AssetsRepository {
    override suspend fun download(url: String): File {
        return runBlocking(scope.coroutineContext) {
            val result = client.download(url)
            if (result !is Right) {
                // todo: use monads
                throw Exception("todo: failed to download")
            }

            val dir = context.cacheDir
            // todo: cache by filename
            val file = File(dir, UUID.randomUUID().toString())
            file.writeBytes(result.value.body)

            file
        }
    }
}