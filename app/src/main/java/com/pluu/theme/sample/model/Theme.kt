package com.pluu.theme.sample.model

/**
 * Represents the available UI themes for the application
 */
enum class Theme(val storageKey: String) {
    Yellow("yellow"),
    Blue("Blue"),
    BlueFromDark("Dark Blue"),
    Purple("Purple"),
    PurpleFromDark("Dark Purple"),
    ;

    companion object {
        val Default = Yellow
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
