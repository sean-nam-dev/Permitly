package com.sean.permitly.core.log

import com.sean.permitly.core.common.Logger
import timber.log.Timber

class TimberLogger(
    private val isDebug: Boolean
) : Logger {

    override fun d(message: String, throwable: Throwable?, tag: String?) {
        if (!isDebug) return
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.d(throwable, message)
    }

    override fun i(message: String, throwable: Throwable?, tag: String?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.i(throwable, message)
    }

    override fun w(message: String, throwable: Throwable?, tag: String?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.w(throwable, message)
    }

    override fun e(message: String, throwable: Throwable?, tag: String?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.e(throwable, message)
    }
}