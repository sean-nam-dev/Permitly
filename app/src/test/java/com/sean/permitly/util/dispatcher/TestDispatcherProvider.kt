package com.sean.permitly.util.dispatcher

import com.sean.permitly.data.dispatcher.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    private val testDispatcher: TestDispatcher
) : DispatcherProvider {

    override val default: CoroutineDispatcher
        get() = testDispatcher

    override val main: CoroutineDispatcher
        get() = Dispatchers.Main

    override val unconfined: CoroutineDispatcher
        get() = testDispatcher

    override val io: CoroutineDispatcher
        get() = testDispatcher
}
