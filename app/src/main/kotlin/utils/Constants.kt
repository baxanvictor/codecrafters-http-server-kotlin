package utils

import dto.HttpVersion

object Constants {
    const val CRLF = "\r\n"
    const val TEXT_PLAIN = "text/plain"

    val HttpVersion1_1 = HttpVersion(1, 1)
}