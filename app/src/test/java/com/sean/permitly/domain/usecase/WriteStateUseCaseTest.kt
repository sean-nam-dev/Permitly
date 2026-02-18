package com.sean.permitly.domain.usecase

import com.sean.permitly.domain.error.Result
import com.sean.permitly.domain.model.State
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class WriteStateUseCaseTest {

    lateinit var fakeAppSettingsRepository: FakeAppSettingsRepository
    lateinit var writeStateUseCase: WriteStateUseCase

    @Before
    fun setup() {
        fakeAppSettingsRepository = FakeAppSettingsRepository()
        writeStateUseCase = WriteStateUseCase(fakeAppSettingsRepository)
    }

    @Test
    fun `returns success`() = runTest {
        assertEquals(
            Result.Success(Unit),
            writeStateUseCase(State.NJ)
        )
    }
}