package com.github.freitzzz.gameboydb.view

import com.github.freitzzz.gameboydb.R
import com.github.freitzzz.gameboydb.data.model.ESRB

fun ESRB.drawableRes() = when (this) {
    ESRB.RATING_PENDING -> R.drawable.esrb_rating_pending
    ESRB.EARLY_CHILDHOOD -> R.drawable.esrb_early_childhood
    ESRB.EVERYONE -> R.drawable.esrb_everyone
    ESRB.EVERYONE_ABOVE_10 -> R.drawable.esrb_everyone_above_10
    ESRB.TEEN -> R.drawable.esrb_teen
    ESRB.MATURE -> R.drawable.esrb_mature
    ESRB.ADULT -> R.drawable.esrb_adult
}