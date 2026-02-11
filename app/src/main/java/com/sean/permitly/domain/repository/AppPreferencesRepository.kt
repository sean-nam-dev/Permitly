package com.sean.permitly.domain.repository

import com.sean.permitly.domain.result.DataError
import com.sean.permitly.domain.result.Result
import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

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
    ): Flow<Result<Boolean, DataError.Local>>

    fun read(
        key: String,
        default: String
    ): Flow<Result<String, DataError.Local>>
}