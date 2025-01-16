package com.github.freitzzz.gameboydb.data.model

/**
 * Enumerates all ESRB ratings.
 * The [value] property is used to identify each rating in a data format (e.g., JSON).
 */
enum class ESRB(val value: Int) {
    RATING_PENDING(0),
    EARLY_CHILDHOOD(1),
    EVERYONE(2),
    EVERYONE_ABOVE_10(3),
    TEEN(4),
    MATURE(5),
    ADULT(6),
}