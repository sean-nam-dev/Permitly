package com.sean.permitly.domain.error

interface ErrorMapper<E> {
    fun map(throwable: Throwable): E
}