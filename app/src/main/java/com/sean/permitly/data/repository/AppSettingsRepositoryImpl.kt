package com.sean.permitly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.data.util.safeCall
import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AppSettingsRepositoryImpl(
    private val dataStore: DataStore<Preferences>,
    private val localErrorMapper: LocalErrorMapper
) : AppSettingsRepository {

    override suspend fun insert(
        key: String,
        value: Boolean
    ): Result<Unit, DataError.Local> {
        return safeCall(localErrorMapper) {
            val pk = booleanPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
    }

    override suspend fun insert(
        key: String,
        value: String
    ): Result<Unit, DataError.Local> {
        return safeCall(localErrorMapper) {
            val pk = stringPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
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