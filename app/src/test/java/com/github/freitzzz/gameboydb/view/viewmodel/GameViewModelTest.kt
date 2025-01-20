package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.github.freitzzz.gameboydb.core.DownloadError
import com.github.freitzzz.gameboydb.core.Left
import com.github.freitzzz.gameboydb.core.NoInternetConnectionError
import com.github.freitzzz.gameboydb.core.Right
import com.github.freitzzz.gameboydb.core.Vault
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.data.model.Game
import com.github.freitzzz.gameboydb.data.model.GamePreview
import com.github.freitzzz.gameboydb.domain.DownloadImage
import com.github.freitzzz.gameboydb.domain.GetTopRatedGames
import com.github.freitzzz.gameboydb.swallow
import io.mockk.awaits
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Rule
import org.junit.Test
import java.io.File

@OptIn(ExperimentalCoroutinesApi::class)
class GameViewModelTest {
    private val getTopRatedGames = mockk<GetTopRatedGames>()
    private val downloadImage = mockk<DownloadImage>()

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    init {
        Dispatchers.setMain(UnconfinedTestDispatcher())

        val vault = Vault()
        vault.store(getTopRatedGames)
        vault.store(downloadImage)

        mockkStatic(::vault)
        every { vault() } returns vault
    }

    @Test
    fun `if getTopRatedTiles call returns Left, then no cover image is downloaded`() = runTest {
        val error = NoInternetConnectionError("turn of wifi!!")
        val viewModel = GamesViewModel()

        coEvery { getTopRatedGames() } returns Left(error)

        viewModel.topRated()
        coVerify(exactly = 0) { downloadImage(any()) }
    }

    @Test
    fun `if getTopRatedTiles call returns Right, then downloadImage is called for every tile cover`() = runTest {
        val url = "https://example.com/foo.bar"
        val coverUri = mockk<Uri>()
        val tile = mockk<GamePreview>()
        val tiles = arrayListOf(tile)
        val viewModel = GamesViewModel()

        every { coverUri.toString() } returns url
        every { tile.thumbnail } returns coverUri
        coEvery { getTopRatedGames() } returns Right(tiles)
        coEvery { downloadImage(any()) } just awaits

        viewModel.topRated()
        coVerify(exactly = tiles.size) { downloadImage(any()) }
    }

    @Test
    fun `the original tile cover is used if downloadImage fails`() = runTest {
        val url = "https://example.com/foo.bar"
        val coverUri = mockk<Uri>()
        val tile = mockk<GamePreview>()
        val tiles = arrayListOf(tile)
        val viewModel = GamesViewModel()
        val error = DownloadError("404")

        every { coverUri.toString() } returns url
        every { tile.copy(thumbnail = any()) } returns tile
        every { tile.thumbnail } returns coverUri
        coEvery { getTopRatedGames() } returns Right(tiles)
        coEvery { downloadImage(any()) } returns Left(error)

        viewModel.topRated()
        verify(exactly = tiles.size) { swallow(tile.copy(thumbnail = coverUri)) }
    }

    @Test
    fun `the downloaded tile cover is used if downloadImage doesn't fail`() = runTest {
        val url = "https://example.com/foo.bar"
        val coverUri = mockk<Uri>()
        val downloadedCoverUri = mockk<Uri>()
        val tile = mockk<GamePreview>()
        val tiles = arrayListOf(tile)
        val viewModel = GamesViewModel()
        val file = mockk<File>()

        mockkStatic(Uri::fromFile)
        every { coverUri.toString() } returns url
        every { tile.copy(thumbnail = any()) } returns tile
        every { tile.thumbnail } returns coverUri
        every { Uri.fromFile(file) }  returns downloadedCoverUri
        coEvery { getTopRatedGames() } returns Right(tiles)
        coEvery { downloadImage(any()) } returns Right(file)

        viewModel.topRated()
        verify(exactly = tiles.size) {  swallow(tile.copy(thumbnail = downloadedCoverUri)) }
    }
}