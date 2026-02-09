package com.sean.permitly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sean.permitly.domain.repository.AppPreferencesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    override suspend fun insert(key: String, value: Boolean) {
        val pk = booleanPreferencesKey(key)
        dataStore.edit { it[pk] = value }
    }

    override suspend fun insert(key: String, value: String) {
        val pk = stringPreferencesKey(key)
        dataStore.edit { it[pk] = value }
    }

    override fun read(
        key: String,
        default: Boolean
    ): Flow<Boolean> {
        val pk = booleanPreferencesKey(key)
        return dataStore.data.map { it[pk] ?: default }
    }

    override fun read(
        key: String,
        default: String
    ): Flow<String> {
        val pk = stringPreferencesKey(key)
        return dataStore.data.map { it[pk] ?: default }
    }
}