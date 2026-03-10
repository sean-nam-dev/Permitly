package com.sean.permitly.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import app.cash.turbine.test
import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.data.repository.AppSettingsRepositoryImpl
import com.sean.permitly.domain.error.Result
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertFalse
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import java.io.File
import java.util.UUID

class AppSettingsRepositoryIntegrationTest {

    private val stringKey = "test_string_key"
    private val booleanKey = "test_boolean_key"

    private lateinit var testFile: File
    private lateinit var dataStore: DataStore<Preferences>
    private lateinit var repository: AppSettingsRepository

    @Before
    fun setup() {
        testFile = File(
            System.getProperty("java.io.tmpdir"),
            "datastore_test_${UUID.randomUUID()}.preferences_pb"
        )

        dataStore = PreferenceDataStoreFactory.create(
            produceFile = { testFile }
        )

        repository = AppSettingsRepositoryImpl(
            logger = mockk(relaxed = true),
            dataStore = dataStore,
            localErrorMapper = LocalErrorMapper()
        )
    }

    @After
    fun tearDown() {
        testFile.delete()
    }

    @Test
    fun `insert Boolean returns Success`() = runTest {
        val result = repository.insert(booleanKey, true)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `insert Boolean persists value`() = runTest {
        repository.insert(booleanKey, true)

        repository.read(booleanKey, false).test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert Boolean overwrites previous value`() = runTest {
        repository.insert(booleanKey, true)
        repository.insert(booleanKey, false)

        repository.read(booleanKey, true).test {
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert String returns Success`() = runTest {
        val result = repository.insert(stringKey, "test")
        assertTrue(result is Result.Success)
    }

    @Test
    fun `insert String persists value`() = runTest {
        repository.insert(stringKey, "test")

        repository.read(stringKey, "default").test {
            assertEquals("test", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert String overwrites previous value`() = runTest {
        repository.insert(stringKey, "first")
        repository.insert(stringKey, "second")

        repository.read(stringKey, "default").test {
            assertEquals("second", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `read Boolean returns default when key not found`() = runTest {
        repository.read(booleanKey, false).test {
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `read Boolean emits updated value after insert`() = runTest {
        repository.read(booleanKey, false).test {
            assertFalse(awaitItem())

            repository.insert(booleanKey, true)

            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `read String returns default when key not found`() = runTest {
        repository.read(stringKey, "default").test {
            assertEquals("default", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `read String emits updated value after insert`() = runTest {
        repository.read(stringKey, "default").test {
            assertEquals("default", awaitItem())

            repository.insert(stringKey, "updated")

            assertEquals("updated", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert with different keys stores independently`() = runTest {
        repository.insert("key1", true)
        repository.insert("key2", false)

        repository.read("key1", false).test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        repository.read("key2", true).test {
            assertFalse(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert empty string persists correctly`() = runTest {
        repository.insert(stringKey, "")

        repository.read(stringKey, "default").test {
            assertEquals("", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `insert with empty key returns success`() = runTest {
        val result = repository.insert("", true)
        assertTrue(result is Result.Success)
    }
}