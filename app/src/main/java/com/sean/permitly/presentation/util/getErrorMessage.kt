package com.sean.permitly.presentation.util

import android.content.Context
import com.sean.permitly.R
import com.sean.permitly.domain.error.DataError

fun Context.getErrorMessage(error: DataError.Local): String {
    return getString(
        when (error) {
            DataError.Local.IO -> R.string.error_io
            DataError.Local.DISK_FULL -> R.string.error_disk_full
            DataError.Local.UNKNOWN -> R.string.error_unknown
        }
    )
}