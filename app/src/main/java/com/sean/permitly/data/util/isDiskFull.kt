package com.sean.permitly.data.util

import androidx.datastore.core.IOException

fun IOException.isDiskFull(): Boolean {
    return message?.contains("ENOSPC", ignoreCase = true) == true ||
            cause?.message?.contains("ENOSPC", ignoreCase = true) == true ||
            message?.contains("No space left", ignoreCase = true) == true
}