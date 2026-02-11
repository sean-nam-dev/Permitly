package com.sean.permitly.domain.result

sealed interface DataError : Error {

    enum class Network : DataError {

    }

    enum class Local : DataError {
        DISK_FULL,
        UNKNOWN
    }
}