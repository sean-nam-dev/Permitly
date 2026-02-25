package com.sean.permitly.data.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey
import com.sean.permitly.core.common.Logger
import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.repository.AppSettingsRepository
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map

class AppSettingsRepositoryImpl(
    private val logger: Logger,
    private val dataStore: DataStore<Preferences>,
    private val localErrorMapper: LocalErrorMapper
) : AppSettingsRepository {

    override suspend fun insert(
        key: String,
        value: Boolean
    ): Result<Unit, DataError.Local> {
        return safeInsert(key) {
            val pk = booleanPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
    }

    override suspend fun insert(
        key: String,
        value: String
    ): Result<Unit, DataError.Local> {
        return safeInsert(key) {
            val pk = stringPreferencesKey(key)
            dataStore.edit { it[pk] = value }
        }
    }

    override fun read(
        key: String,
        default: Boolean
    ): Flow<Boolean> {
        val pk = booleanPreferencesKey(key)
        return safeRead(
            key = key,
            default = default,
            preferencesKey = pk
        )
    }

    override fun read(
        key: String,
        default: String
    ): Flow<String> {
        val pk = stringPreferencesKey(key)
        return safeRead(
            key = key,
            default = default,
            preferencesKey = pk
        )
    }

    private suspend inline fun <T> safeInsert(
        key: String,
        crossinline action: suspend () -> T
    ): Result<Unit, DataError.Local> {
        return try {
            action()
            Result.Success(Unit)
        } catch (e: CancellationException) {
            throw e
        } catch (e: Exception) {
            logger.e(
                message = INSERT_ERROR_MESSAGE + key,
                throwable = e,
                tag = TAG
            )
            Result.Error(localErrorMapper.map(e))
        }
    }

    private fun <T> safeRead(
        key: String,
        default: T,
        preferencesKey: Preferences.Key<T>
    ): Flow<T> {
        return dataStore.data
            .catch { e ->
                logger.e(
                    message = READ_ERROR_MESSAGE + key,
                    throwable = e,
                    tag = TAG
                )

                if (e is IOException) {
                    emit(emptyPreferences())
                } else {
                    throw e
                }
            }
            .map { it[preferencesKey] ?: default }
    }

    companion object {
        private const val TAG = "AppSettingsRepositoryImpl"
        private const val READ_ERROR_MESSAGE = "Read failed for key = "
        private const val INSERT_ERROR_MESSAGE = "Insert ended with error for key = "
    }
}