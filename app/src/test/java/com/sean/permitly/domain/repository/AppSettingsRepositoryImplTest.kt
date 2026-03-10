package com.sean.permitly.domain.repository

import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import app.cash.turbine.test
import com.sean.permitly.data.error.LocalErrorMapper
import com.sean.permitly.data.repository.AppSettingsRepositoryImpl
import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.logger.Logger
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Test
import kotlin.coroutines.cancellation.CancellationException
import kotlin.test.assertFailsWith

class AppSettingsRepositoryImplTest {

    private val logger: Logger = mockk(relaxed = true)
    private val dataStore: DataStore<Preferences> = mockk()
    private val localErrorMapper: LocalErrorMapper = LocalErrorMapper()

    private val repository = AppSettingsRepositoryImpl(
        logger = logger,
        dataStore = dataStore,
        localErrorMapper = localErrorMapper
    )

    private val bKey = "boolean_key"
    private val sKey = "string_key"

    @After
    fun tearDown() {
        clearAllMocks()
    }

    @Test
    fun `insert returns Success when datastore succeeds`() = runTest {
        coEvery { dataStore.updateData(any()) } returns mockk(relaxed = true)

        val booleanResult = repository.insert("test", false)
        val stringResult = repository.insert("test", "text")

        assertTrue(booleanResult is Result.Success)
        assertTrue(stringResult is Result.Success)
    }

    @Test
    fun `insert returns error and logs when exception occurs`() = runTest {
        coEvery { dataStore.updateData(any()) } throws Exception()

        val booleanResult = repository.insert(bKey, false)
        val stringResult = repository.insert(sKey, "text")

        assertTrue(booleanResult is Result.Error)
        assertTrue(stringResult is Result.Error)

        verify(exactly = 2) {
            logger.e(
                tag = any(),
                message = any(),
                throwable = any()
            )
        }
    }

    @Test
    fun `insert rethrows CancellationException without logging`() = runTest {
        coEvery { dataStore.updateData(any()) } throws CancellationException()

        assertFailsWith<CancellationException> {
            repository.insert(bKey, false)
        }

        verify(exactly = 0) {
            logger.e(
                tag = any(),
                message = any(),
                throwable = any()
            )
        }
    }

    @Test
    fun `read emits value from datastore`() = runTest {
        val preferences: Preferences = mockk()
        every { dataStore.data } returns flowOf(preferences)

        every { preferences[booleanPreferencesKey(bKey)] } returns true

        repository.read(bKey, false).test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        every { preferences[stringPreferencesKey(sKey)] } returns "text"

        repository.read(sKey, "default").test {
            assertEquals("text", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `read emits default and logs when IOException occurs`() = runTest {
        every { dataStore.data } returns flow { throw IOException() }

        repository.read(bKey, true).test {
            assertTrue(awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        repository.read(sKey, "default").test {
            assertEquals("default", awaitItem())
            cancelAndIgnoreRemainingEvents()
        }

        verify(exactly = 2) {
            logger.e(
                tag = any(),
                message = any(),
                throwable = any<IOException>()
            )
        }
    }

    @Test
    fun `read rethrows non IOException and logs error`() = runTest {
        every { dataStore.data } returnsMany listOf(
            flow { throw RuntimeException() },
            flow { throw RuntimeException() }
        )

        repository.read(bKey, true).test {
            awaitError().also { assertTrue(it is RuntimeException) }
        }

        repository.read(sKey, "default").test {
            awaitError().also { assertTrue(it is RuntimeException) }
        }

        verify(exactly = 2) {
            logger.e(
                tag = any(),
                message = any(),
                throwable = any<RuntimeException>()
            )
        }
    }
}