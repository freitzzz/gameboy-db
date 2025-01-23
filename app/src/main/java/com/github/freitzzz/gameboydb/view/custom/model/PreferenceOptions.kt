package com.github.freitzzz.gameboydb.view.custom.model

data class PreferenceOptions(
    val label: String,
    val value: String,
    val options: List<String>,
    val onOptionSelected: (String) -> Unit = {}
)