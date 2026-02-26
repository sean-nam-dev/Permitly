package com.sean.permitly.core.common

interface Logger {

    fun d(tag: String? = null, message: String, throwable: Throwable? = null)

    fun i(tag: String? = null, message: String, throwable: Throwable? = null)

    fun w(tag: String? = null, message: String, throwable: Throwable? = null)

    fun e(tag: String? = null, message: String, throwable: Throwable? = null)
}