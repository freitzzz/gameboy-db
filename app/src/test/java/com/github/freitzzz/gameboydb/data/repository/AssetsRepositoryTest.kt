package com.github.freitzzz.gameboydb.data.repository

import android.content.Context
import com.github.freitzzz.gameboydb.core.DownloadError
import com.github.freitzzz.gameboydb.core.Left
import com.github.freitzzz.gameboydb.core.NoInternetConnectionError
import com.github.freitzzz.gameboydb.core.OperationError
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.data.http.NetworkingClient
import com.github.freitzzz.gameboydb.data.http.Response
import io.mockk.coEvery
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.slot
import io.mockk.unmockkAll
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Test
import java.io.File
import java.io.FileFilter

class NetworkingAssetsRepositoryTest {
    private val scope = CoroutineScope(Dispatchers.Unconfined)
    private val client = mockk<NetworkingClient>()
    private val context = mockk<Context>()

    @After
    fun teardown() {
        unmockkAll()
    }

    @Test
    fun `returns DownloadError on the left side if url does not provide a resource path`() =
        runTest {
            val url = "https://google.com"
            val repository = NetworkingAssetsRepository(client, context, scope)

            val result = repository.download(url)
            assertEquals(
                Left<DownloadError, File>(DownloadError("not a valid url (=> $url)")),
                result
            )
        }

    @Test
    fun `returns File on the right side if file has been downloaded already`() = runTest {
        val filename = "foo.bar"
        val url = "https://google.com/$filename"
        val file = File(filename)
        val cacheDir = mockk<File>()
        val repository = NetworkingAssetsRepository(client, context, scope)

        every { cacheDir.listFiles(any<FileFilter>()) } returns arrayOf(file)
        every { context.cacheDir } returns cacheDir

        val result = repository.download(url)
        assertEquals(Right<OperationError, File>(file), result)
    }

    @Test
    fun `returns OperationalError on the left side if download fails`() = runTest {
        val filename = "foo.bar"
        val url = "https://google.com/$filename"
        val cacheDir = mockk<File>()
        val error = NoInternetConnectionError("turn on wifi!!")
        val repository = NetworkingAssetsRepository(client, context, scope)

        every { cacheDir.listFiles(any<FileFilter>()) } returns arrayOf()
        every { context.cacheDir } returns cacheDir
        coEvery { client.download(url) } returns Left(error)

        val result = repository.download(url)
        assertEquals(Left<OperationError, File>(error), result)
    }

    @Test
    fun `returns File that has been saved in cacheDir on the right side if download doesn't fail`() = runTest {
        val dirname = "cache"
        val filename = "foo.bar"
        val url = "https://google.com/$filename"
        val file = File(dirname, filename)
        val cacheDir = mockk<File>()
        val response = mockk<Response>()
        val repository = NetworkingAssetsRepository(client, context, scope)
        mockkStatic(File::writeBytes)

        every { cacheDir.listFiles(any<FileFilter>()) } returns arrayOf()
        every { cacheDir.path } returns dirname
        every { context.cacheDir } returns cacheDir
        every { response.body } returns ByteArray(0)
        coEvery { client.download(url) } returns Right(response)

        val slot = slot<File>()
        justRun { capture(slot).writeBytes(any()) }

        val result = repository.download(url)
        assertEquals(Right<OperationError, File>(file), result)
    }

    @Test
    fun `returns Left even if writeBytes call fails`() = runTest {
        val dirname = "cache"
        val filename = "foo.bar"
        val url = "https://google.com/$filename"
        val cacheDir = mockk<File>()
        val response = mockk<Response>()
        val repository = NetworkingAssetsRepository(client, context, scope)
        mockkStatic(File::writeBytes)

        every { cacheDir.listFiles(any<FileFilter>()) } returns arrayOf()
        every { cacheDir.path } returns dirname
        every { context.cacheDir } returns cacheDir
        every { response.body } returns ByteArray(0)
        coEvery { client.download(url) } returns Right(response)

        val slot = slot<File>()
        every { capture(slot).writeBytes(any()) } throws Exception("write error!!")

        val result = repository.download(url)
        assert(result is Left)
    }
}