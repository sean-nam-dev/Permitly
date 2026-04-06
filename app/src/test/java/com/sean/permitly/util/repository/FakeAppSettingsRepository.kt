package com.sean.permitly.util.repository

import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.repository.AppSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

class FakeAppSettingsRepository : AppSettingsRepository {

    private val booleanStorage = mutableMapOf<String, MutableStateFlow<Boolean>>()
    private val stringStorage = mutableMapOf<String, MutableStateFlow<String>>()

    override suspend fun insert(key: String, value: Boolean): Result<Unit, DataError.Local> {
        booleanStorage.getOrPut(key) { MutableStateFlow(value) }.value = value
        return Result.Success(Unit)
    }

    override suspend fun insert(key: String, value: String): Result<Unit, DataError.Local> {
        stringStorage.getOrPut(key) { MutableStateFlow(value) }.value = value
        return Result.Success(Unit)
    }

    override fun read(key: String, default: Boolean): Flow<Boolean> =
        booleanStorage.getOrPut(key) { MutableStateFlow(default) }

    override fun read(key: String, default: String): Flow<String> =
        stringStorage.getOrPut(key) { MutableStateFlow(default) }
}