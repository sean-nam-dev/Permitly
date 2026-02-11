package com.sean.permitly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sean.permitly.data.util.isDiskFull
import com.sean.permitly.domain.repository.AppPreferencesRepository
import com.sean.permitly.domain.result.DataError
import com.sean.permitly.domain.result.Result
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AppPreferencesRepositoryImpl(
    private val dataStore: DataStore<Preferences>
) : AppPreferencesRepository {

    override suspend fun insert(
        key: String,
        value: Boolean
    ): Result<Unit, DataError.Local> {
        return tryCatch {
            val pk = booleanPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
    }

    override suspend fun insert(
        key: String,
        value: String
    ): Result<Unit, DataError.Local> {
        return tryCatch {
            val pk = stringPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
    }

    override fun read(
        key: String,
        default: Boolean
    ): Flow<Result<Boolean, DataError.Local>> {
        val pk = booleanPreferencesKey(key)
        return dataStore.data
            .map { Result.Success(it[pk] ?: default) }
            .catch { emit(Result.Success(default)) }
    }

    override fun read(
        key: String,
        default: String
    ): Flow<Result<String, DataError.Local>> {
        val pk = stringPreferencesKey(key)
        return dataStore.data
            .map { Result.Success(it[pk] ?: default) }
            .catch { emit(Result.Success(default)) }
    }

    private suspend fun tryCatch(action: suspend () -> Unit): Result<Unit, DataError.Local> {
        return try {
            action()
            Result.Success(Unit)
        } catch (e: IOException) {
            if (e.isDiskFull()) Result.Error(DataError.Local.DISK_FULL)
            else throw e
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }
}