package com.sean.permitly.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.data.repository.AppSettingsRepositoryImpl
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.UUID

@OptIn(ExperimentalCoroutinesApi::class)
class AppSettingsRepositoryTest {

    private val stringKey = "test_string_key"
    private val stringDefault = "test_string_default"

    private val booleanKey = "test_boolean_key"

    private lateinit var testFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var appSettingsRepository: AppSettingsRepository

    @Before
    fun setup() {
        testFile = File(
            System.getProperty("java.io.tmpdir"),
            "datastore_test_$${UUID.randomUUID()}.preferences_pb"
        )

        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { testFile }
        )

        appSettingsRepository = AppSettingsRepositoryImpl(
            dataStore = dataStore,
            localErrorMapper = LocalErrorMapper()
        )
    }

    @After
    fun tearDown() {
        testFile.delete()
    }

    @Test
    fun `emits default string-value when key does not exists`() = runTest {
        appSettingsRepository.read(stringKey, stringDefault).test {
            assertEquals(stringDefault, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits default boolean-value when key does not exists`() = runTest {
        appSettingsRepository.read(booleanKey, true).test {
            assertEquals(true, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits updated string-value after insert`() = runTest {
        appSettingsRepository.read(stringKey, stringDefault).test {

            assertEquals(stringDefault, awaitItem())

            val newValue = "new_value"
            appSettingsRepository.insert(stringKey, newValue)
            assertEquals(newValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits updated boolean-value after insert`() = runTest {
        val defaultValue = false

        appSettingsRepository.read(booleanKey, defaultValue).test {

            assertEquals(defaultValue, awaitItem())

            val newValue = true
            appSettingsRepository.insert(booleanKey, newValue)
            assertEquals(newValue, awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits string-values in order`() = runTest {
        val list = List(10) { it.toString() }

        appSettingsRepository.read(stringKey, stringDefault).test {

            assertEquals(stringDefault, awaitItem())

            list.forEach {
                appSettingsRepository.insert(stringKey, it)
                assertEquals(it, awaitItem())
            }

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `emits boolean-values in order`() = runTest {
        val list = listOf(true, false)

        appSettingsRepository.read(booleanKey, false).test {

            assertEquals(false, awaitItem())

            list.forEach {
                appSettingsRepository.insert(booleanKey, it)
                assertEquals(it, awaitItem())
            }

            cancelAndIgnoreRemainingEvents()
        }
    }
}
