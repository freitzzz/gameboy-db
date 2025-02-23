package com.github.freitzzz.gameboydb.data.model

import android.net.Uri
import com.github.freitzzz.gameboydb.core.getStrings
import com.github.freitzzz.gameboydb.core.map
import org.json.JSONObject

fun JSONObject.gamePreview() = GamePreview(
    id = get("id").toString(),
    name = getString("name"),
    genres = getStrings("genres"),
    platforms = getStrings("platforms"),
    thumbnail = optJSONObject("thumbnail")?.let { Uri.parse(it.getString("url")) }
)

fun JSONObject.game() = Game(
    id = get("id").toString(),
    name = getString("name"),
    description = getString("description"),
    releaseYear = getInt("releaseYear"),
    developers = getStrings("developers"),
    publishers = getStrings("publishers"),
    screenshots = getJSONArray("screenshots").map { Uri.parse(it.getString("url")) },
    esrb = ESRB.entries[getInt("esrb")],
    promo = optString("promo"),
    trivia = optString("trivia"),
    rating = optInt("rating").toFloat(),
    critics = optInt("critics").toFloat(),
    thumbnail = optJSONObject("thumbnail")?.let { Uri.parse(it.getString("url")) },
    cover = optJSONObject("cover")?.let { Uri.parse(it.getString("url")) },
    gameplay = optJSONObject("gameplay")?.let { Uri.parse(it.getString("url")) },
    genres = getStrings("genres"),
    platforms = getStrings("platforms"),
)