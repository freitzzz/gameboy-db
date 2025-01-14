package com.github.freitzzz.gameboydb

/**
 * "Swallows" any value passed in the function so that the IDE sees that the value is being used.
 * Use this function whenever the IDE reports that the return value is not being used in your tests.
 */
fun swallow(vararg any: Any) = Unit