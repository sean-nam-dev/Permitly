package com.sean.permitly.util.dispatcher

import com.sean.permitly.data.dispatcher.DispatcherProvider
import kotlinx.coroutines.test.TestDispatcher

class TestDispatcherProvider(
    testDispatcher: TestDispatcher
) : DispatcherProvider {
    override val default = testDispatcher
    override val main = testDispatcher
    override val unconfined = testDispatcher
    override val io = testDispatcher
}
