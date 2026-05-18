package utils

import java.net.URI

fun String.toUri(): URI? {
    return runCatching { URI(this) }.getOrNull()
}
