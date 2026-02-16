package com.sean.permitly.domain.error

sealed interface DataError : Error {

    enum class Network : DataError {

    }

    enum class Local : DataError {
        IO,
        DISK_FULL,
        UNKNOWN
    }
}