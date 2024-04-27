package com.pluu.theme.sample.model

/**
 * Represents the available UI themes for the application
 */
enum class Theme(val storageKey: String) {
    Light("yellow"),
    Dark("Dark Purple");

    val isLight: Boolean
        get() = when (this) {
            Light -> true
            else -> false
        }

    companion object {
        val Default = Light
        val DarkDefault = Dark

        fun of(ordinal: Int): Theme {
            return Theme.entries[ordinal]
        }
    }
}

/**
 * Returns the matching [Theme] for the given [storageKey] value.
 */
fun themeFromStorageKey(storageKey: String): Theme? {
    return Theme.entries.firstOrNull { it.storageKey == storageKey }
}
