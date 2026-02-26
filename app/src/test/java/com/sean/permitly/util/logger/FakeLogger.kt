package com.sean.permitly.util.logger

import com.sean.permitly.core.common.Logger

class FakeLogger : Logger {
    override fun d(tag: String?, message: String, throwable: Throwable?) {}
    override fun i(tag: String?, message: String, throwable: Throwable?) {}
    override fun w(tag: String?, message: String, throwable: Throwable?) {}
    override fun e(tag: String?, message: String, throwable: Throwable?) {}
}