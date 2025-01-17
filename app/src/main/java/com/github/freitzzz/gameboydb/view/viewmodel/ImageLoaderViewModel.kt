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

    operator fun invoke(image: Uri): LiveData<Uri> {
        return MutableLiveData<Uri>().apply {
            viewModelScope.launch {
                onIO {
                    val result = downloadImage(image.toString())
                    result.each {
                        postValue(Uri.fromFile(it))
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
                        val result = downloadImage(value[i].toString())
                        result.each {
                            value[i] = Uri.fromFile(it)
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