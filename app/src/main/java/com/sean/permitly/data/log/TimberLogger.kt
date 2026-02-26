package com.sean.permitly.data.log

import com.sean.permitly.core.common.Logger
import timber.log.Timber

class TimberLogger : Logger {

    override fun d(tag: String?, message: String, throwable: Throwable?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.d(throwable, message)
    }

    override fun i(tag: String?, message: String, throwable: Throwable?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.i(throwable, message)
    }

    override fun w(tag: String?, message: String, throwable: Throwable?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.w(throwable, message)
    }

    override fun e(tag: String?, message: String, throwable: Throwable?) {
        val timber = tag?.let { Timber.tag(it) } ?: Timber
        timber.e(throwable, message)
    }
}