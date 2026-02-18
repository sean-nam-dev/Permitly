package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ReadFirstLaunchUseCaseTest {

    lateinit var fakeAppSettingsRepository: FakeAppSettingsRepository
    lateinit var readFirstLaunchUseCase: ReadFirstLaunchUseCase
    lateinit var key: String

    @Before
    fun setup() {
        fakeAppSettingsRepository = FakeAppSettingsRepository()
        readFirstLaunchUseCase = ReadFirstLaunchUseCase(fakeAppSettingsRepository)
        key = AppSettingsKeys.FIRST_LAUNCH
    }

    @Test
    fun `returns true as default`() = runTest {
        assertTrue(readFirstLaunchUseCase().first())
    }

    @Test
    fun `returns assigned value`() = runTest {
        fakeAppSettingsRepository.insert(key, false)
        assertFalse(readFirstLaunchUseCase().first())

        fakeAppSettingsRepository.insert(key, true)
        assertTrue(readFirstLaunchUseCase().first())
    }
}