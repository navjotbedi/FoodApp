package com.toptal.core.cache.preference

import androidx.datastore.preferences.core.Preferences
import kotlinx.coroutines.flow.Flow

interface PreferenceCache {
    fun getRawData(): Flow<Preferences>

    fun getString(key: String, default: String = ""): Flow<String>

    suspend fun putString(key: String, value: String)
}