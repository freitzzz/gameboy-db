package com.github.freitzzz.gameboydb.view.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.github.freitzzz.gameboydb.core.onIO
import com.github.freitzzz.gameboydb.core.vault
import com.github.freitzzz.gameboydb.domain.DownloadImage
import kotlinx.coroutines.launch

class ImageLoaderViewModel : ViewModel() {
    private val downloadImage by lazy { vault().get<DownloadImage>() }

    operator fun invoke(image: Uri, block: (Uri) -> Unit) {
        val url = image.toString()
        val cached = loadedImages[url]

        if (cached != null) {
            return block(cached)
        }

        viewModelScope.launch {
            onIO {
                val result = downloadImage(url)
                result.each {
                    val uri = Uri.fromFile(it)
                    loadedImages[url] = uri

                    viewModelScope.launch {
                        block(uri)
                    }
                }
            }
        }
    }

    operator fun invoke(images: List<Uri>): LiveData<List<Uri>> {
        val value = images.toMutableList()
        return MutableLiveData<List<Uri>>().apply {
            viewModelScope.launch {
                onIO {
                    for (i in value.indices) {
                        val url = value[i].toString()
                        val result = downloadImage(url)
                        result.each {
                            value[i] = Uri.fromFile(it)
                            loadedImages[url] = value[i]
                            postValue(value)
                        }
                    }
                }
            }
        }
    }

    fun loaded(vararg uris: Uri) = uris.all { it.scheme == "file" }
    fun loaded(uris: List<Uri>) = uris.all { it.scheme == "file" }
}

/**
 * Global cache of downloaded images mapped by their urls.
 */
internal val loadedImages = mutableMapOf<String, Uri>()

/**
 * Check if this image has been loaded already in [loadedImages], and if so uses that instance.
 */
fun Uri.cached() = loadedImages[toString()] ?: this

/**
 * Applies [Uri.cached] to a list of [Uri] instances.
 */
fun List<Uri>.cached() = map { it.cached() }