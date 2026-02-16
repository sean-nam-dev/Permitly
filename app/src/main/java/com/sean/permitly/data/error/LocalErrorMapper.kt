package com.sean.permitly.data.error

import androidx.datastore.core.IOException
import com.sean.permitly.domain.error.DataError
import com.sean.permitly.domain.error.ErrorMapper

class LocalErrorMapper : ErrorMapper<DataError.Local> {
    override fun map(throwable: Throwable): DataError.Local {
        return when (throwable) {
            is IOException -> {
                if (isDiskFull(throwable)) {
                    DataError.Local.DISK_FULL
                } else {
                    DataError.Local.IO
                }
            }

            else -> {
                DataError.Local.UNKNOWN
            }
        }
    }

    private fun isDiskFull(exception: IOException): Boolean {
        return exception.message?.contains("ENOSPC", true) == true ||
                exception.cause?.message?.contains("ENOSPC", true) == true ||
                exception.message?.contains("No space left", true) == true
    }
}