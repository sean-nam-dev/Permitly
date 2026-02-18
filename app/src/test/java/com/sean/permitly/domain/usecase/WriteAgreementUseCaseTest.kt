package com.sean.permitly.domain.usecase

import com.sean.permitly.domain.error.Result
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WriteAgreementUseCaseTest {

    lateinit var fakeAppSettingsRepository: FakeAppSettingsRepository
    lateinit var writeAgreementUseCase: WriteAgreementUseCase

    @Before
    fun setup() {
        fakeAppSettingsRepository = FakeAppSettingsRepository()
        writeAgreementUseCase = WriteAgreementUseCase(fakeAppSettingsRepository)
    }

    @Test
    fun `returns success`() = runTest {
        assertEquals(
            Result.Success(Unit),
            writeAgreementUseCase(true)
        )
    }
}