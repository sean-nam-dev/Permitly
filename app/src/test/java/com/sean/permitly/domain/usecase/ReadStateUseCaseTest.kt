package com.sean.permitly.domain.usecase

import com.sean.permitly.data.util.AppSettingsKeys
import com.sean.permitly.domain.model.State
import com.sean.permitly.util.repository.FakeAppSettingsRepository
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ReadStateUseCaseTest {

    lateinit var fakeAppSettingsRepository: FakeAppSettingsRepository
    lateinit var readStateUseCase: ReadStateUseCase
    lateinit var key: String

    @Before
    fun setup() {
        fakeAppSettingsRepository = FakeAppSettingsRepository()
        readStateUseCase = ReadStateUseCase(fakeAppSettingsRepository)
        key = AppSettingsKeys.STATE
    }

    @Test
    fun `returns NONE as default`() = runTest {
        assertEquals(State.NONE, readStateUseCase().first())
    }

    @Test
    fun `returns assigned value`() = runTest {
        fakeAppSettingsRepository.insert(key, State.NJ.name)
        assertEquals(State.NJ, readStateUseCase().first())

        fakeAppSettingsRepository.insert(key, State.NY.name)
        assertEquals(State.NY, readStateUseCase().first())
    }
}