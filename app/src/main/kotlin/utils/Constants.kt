package utils

import dto.HttpVersion

object Constants {
    const val CRLF = "\r\n"
    const val TEXT_PLAIN = "text/plain"
    const val APPLICATION_OCTET_STREAM = "application/octet-stream"

    val HttpVersion1_1 = HttpVersion(1, 1)
}