package com.github.freitzzz.gameboydb.core

import org.json.JSONArray
import org.json.JSONObject

/**
 * Returns the value behind the key as a list of strings.
 */
fun JSONObject.getStrings(name: String) = getJSONArray(name).asStrings()

/**
 * Transforms the array as a list of strings.
 */
fun JSONArray.asStrings() = buildList<String> {
    for (i in 0..<this@asStrings.length()) add(this@asStrings.getString(i))
}

/**
 * Transforms the array as a list of [T].
 */
fun <T> JSONArray.map(transform: (JSONObject) -> T) = buildList {
    for (i in 0..<this@map.length()) add(transform(this@map.getJSONObject(i)))
}