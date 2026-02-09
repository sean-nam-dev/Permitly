package com.sean.permitly.domain.repository

import kotlinx.coroutines.flow.Flow

interface AppPreferencesRepository {

    suspend fun insert(key: String, value: Boolean)

    suspend fun insert(key: String, value: String)

    fun read(key: String, default: Boolean): Flow<Boolean>

    fun read(key: String, default: String): Flow<String>
}