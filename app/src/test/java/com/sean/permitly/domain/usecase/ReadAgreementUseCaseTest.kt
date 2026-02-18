package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ReadAgreementUseCaseTest {

    lateinit var fakeAppSettingsRepository: FakeAppSettingsRepository
    lateinit var readAgreementUseCase: ReadAgreementUseCase
    lateinit var key: String

    @Before
    fun setup() {
        fakeAppSettingsRepository = FakeAppSettingsRepository()
        readAgreementUseCase = ReadAgreementUseCase(fakeAppSettingsRepository)
        key = AppSettingsKeys.AGREEMENT
    }

    @Test
    fun `returns false as default`() = runTest {
        assertFalse(readAgreementUseCase().first())
    }

    @Test
    fun `returns assigned value`() = runTest {
        fakeAppSettingsRepository.insert(key, true)
        assertTrue(readAgreementUseCase().first())

        fakeAppSettingsRepository.insert(key, false)
        assertFalse(readAgreementUseCase().first())
    }
}