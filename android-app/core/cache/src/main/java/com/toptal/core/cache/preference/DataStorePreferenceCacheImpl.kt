package com.toptal.core.cache.preference

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStorePreferenceCacheImpl @Inject constructor(private val dataStore: DataStore<Preferences>) : PreferenceCache {
    override fun getRawData(): Flow<Preferences> = dataStore.data.catch { emit(emptyPreferences()) }
    override fun getString(key: String, default: String): Flow<String> = getRawData().map { it[stringPreferencesKey(key)] ?: default }
    override suspend fun putString(key: String, value: String) {
        dataStore.edit { it[stringPreferencesKey(key)] = value }
    }
}