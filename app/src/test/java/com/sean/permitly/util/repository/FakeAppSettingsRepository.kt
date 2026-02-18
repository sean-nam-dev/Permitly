package com.sean.permitly.util.repository

import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeAppSettingsRepository : AppSettingsRepository {

    private val storage = mutableMapOf<String, Any>()

    override suspend fun insert(
        key: String,
        value: Boolean
    ): Result<Unit, DataError.Local> {
        return try {
            storage[key] = value
            Result.Success(Unit)
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override suspend fun insert(
        key: String,
        value: String
    ): Result<Unit, DataError.Local> {
        return try {
            storage[key] = value
            Result.Success(Unit)
        } catch (_: Exception) {
            Result.Error(DataError.Local.UNKNOWN)
        }
    }

    override fun read(
        key: String,
        default: Boolean
    ): Flow<Boolean> {
        return flowOf(
            storage.getOrDefault(key, default) as Boolean
        )
    }

    override fun read(
        key: String,
        default: String
    ): Flow<String> {
        return flowOf(
            storage.getOrDefault(key, default) as String
        )
    }
}