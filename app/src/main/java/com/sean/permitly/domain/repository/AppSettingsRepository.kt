package com.sean.permitly.domain.repository

import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.Result
import kotlinx.coroutines.flow.Flow

interface AppSettingsRepository {

    suspend fun insert(
        key: String,
        value: Boolean
    ): Result<Unit, DataError.Local>

    suspend fun insert(
        key: String,
        value: String
    ): Result<Unit, DataError.Local>

    fun read(
        key: String,
        default: Boolean
    ): Flow<Boolean>

    fun read(
        key: String,
        default: String
    ): Flow<String>
}