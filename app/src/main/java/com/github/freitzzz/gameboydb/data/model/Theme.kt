package com.github.freitzzz.gameboydb.data.model

import android.os.Parcelable
import androidx.annotation.StringRes
import kotlinx.parcelize.Parcelize

@Parcelize
data class Theme(@StringRes val id: Int, val value: String) : Parcelable {
    override fun toString() = value
}