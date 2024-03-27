package com.pluu.theme.sample.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.pluu.theme.sample.data.DataStorePreferenceStorage.PreferencesKeys.PREF_SELECTED_THEME
import com.pluu.theme.sample.model.Theme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

interface PreferenceStorage {
    suspend fun selectTheme(theme: String)
    val selectedTheme: Flow<String>
}

@Singleton
class DataStorePreferenceStorage @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferenceStorage {

    companion object {
        const val PREFS_NAME = "iosched"
    }

    object PreferencesKeys {
        val PREF_SELECTED_THEME = stringPreferencesKey("pref_dark_mode")
    }

    override suspend fun selectTheme(theme: String) {
        dataStore.edit {
            it[PREF_SELECTED_THEME] = theme
        }
    }

    override val selectedTheme =
        dataStore.data.map { it[PREF_SELECTED_THEME] ?: Theme.DEFAULT.storageKey }
}
