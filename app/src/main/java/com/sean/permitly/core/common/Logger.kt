package com.sean.permitly.core.common

interface Logger {

    fun d(message: String, throwable: Throwable? = null, tag: String? = null)

    fun i(message: String, throwable: Throwable? = null, tag: String? = null)

    fun w(message: String, throwable: Throwable? = null, tag: String? = null)

    fun e(message: String, throwable: Throwable? = null, tag: String? = null)
}